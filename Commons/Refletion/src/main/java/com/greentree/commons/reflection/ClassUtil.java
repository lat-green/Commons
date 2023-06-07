package com.greentree.commons.reflection;

import com.greentree.commons.reflection.finder.AllPacakagesClassFinder;
import com.greentree.commons.reflection.finder.ClassLoaderClassFinder;
import com.greentree.commons.reflection.soure.BootStrupCodeSource;
import com.greentree.commons.reflection.soure.CodeSource;
import com.greentree.commons.util.exception.WrappedException;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.*;

public final class ClassUtil {

    private static ArrayList<CodeSource> sources;
    private static Collection<Class<?>> classes;
    private static Collection<String> packages;

    public static <T> Class<? extends T> loadClassInAllPackages(Class<T> baseClass, String className) throws ClassNotFoundException {
        var finder = new AllPacakagesClassFinder(
                new ClassLoaderClassFinder()
        );
        return finder.findClass(className, baseClass);
    }

    public static Stream<String> getAllPackages() {
        if (packages == null) {
            final var packages_list = new ArrayList<String>(1024);
            packages = packages_list;
            final var m = ClassUtil.class.getModule();
            if (m != null) {
                final var l = m.getLayer();
                if (l != null)
                    for (var mod : l.modules())
                        packages_list.addAll(mod.getPackages());
            }
            packages_list.trimToSize();
        }
        return packages.stream();
    }

    public static Stream<Class<?>> getAllClasses() {
        if (classes == null) classes = sources().stream().flatMap(x -> x.getAllClasses()).toList();
        return classes.stream();
    }

    private static Collection<CodeSource> sources() {
        if (sources == null) {
            sources = new ArrayList<>(64);
            var loader = ClassUtil.class.getClassLoader();
            try {
                var res = loader.getResources("META-INF/").asIterator();
                while (res.hasNext())
                    sources.add(CodeSource.create(res.next()));
                sources.add(new BootStrupCodeSource());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sources.trimToSize();
        }
        return sources;
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(T t) {
        if (t == null)
            return null;
        var type = (Class<T>) t.getClass();
        if (!isMutable(t))
            return t;
        if (type.isArray()) {
            final var len = Array.getLength(t);
            final var arr = (T) Array.newInstance(type.componentType(), len);
            for (var i = 0; i < len; i++) {
                final var e = Array.get(arr, i);
                final var c = clone(e);
                Array.set(arr, i, c);
            }
            return arr;
        }
        {//use methor clone
            Method clone;
            boolean flag;
            try {
                clone = type.getMethod("clone");
                flag = clone.canAccess(t);
                clone.setAccessible(true);
                clone.invoke(t);
                clone.setAccessible(flag);
            } catch (NoSuchMethodException e) {
            } catch (IllegalAccessException | IllegalArgumentException
                     | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        {
            try (final var bout = new ByteArrayOutputStream();
                 final var out = new ObjectOutputStream(bout)) {
                out.writeObject(t);
                try (final var bin = new ByteArrayInputStream(bout.toByteArray());
                     final var in = new ObjectInputStream(bin)) {
                    return (T) in.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        T copy;
        try {
            copy = ObjectBuilder.get(type, ObjectBuilder.getArgumentsMap(t), obj -> clone(obj.v3));
        } catch (Exception e) {
            throw new IllegalArgumentException("method clone not accessible " + t, e);
        }
        return copy;
    }

    public static <T> void copyAllFieldsTo(T t, T dest) throws Exception {
        for (Field f : ClassUtil.getAllFields(t.getClass())) {
            var m = f.getModifiers();
            if (!(isStatic(m) | isTransient(m)))
                try {
                    var flag = f.canAccess(t);
                    f.setAccessible(true);
                    f.set(dest, f.get(t));
                    f.setAccessible(flag);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw e;
                }
        }
    }

    public static List<Field> getAllFields(final Class<?> clazz) {
        final List<Field> list = new LinkedList<>();
        for (final Class<?> c : ClassUtil.getAllPerant(clazz))
            Collections.addAll(list, c.getDeclaredFields());
        return list;
    }

    public static Collection<Class<?>> getAllPerant(Class<?> clazz) {
        final Collection<Class<?>> list = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            list.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    public static <A extends Annotation> Collection<A> getAllAnnotations(final Class<?> clazz,
                                                                         final Class<A> annotationClass) {
        final Collection<A> res = new ArrayList<>();
        if (clazz == null || clazz.equals(Object.class) || annotationClass == null)
            return res;
        for (final Class<?> c : clazz.getInterfaces()) {
            final var annotation = c.getDeclaredAnnotation(annotationClass);
            if (annotation != null)
                res.add(annotation);
        }
        final var annotation = clazz.getDeclaredAnnotation(annotationClass);
        if (annotation != null)
            res.add(annotation);
        res.addAll(ClassUtil.getAllAnnotations(clazz.getSuperclass(), annotationClass));
        return res;
    }

    public static <A extends Annotation> List<Field> getAllFieldsHasAnnotation(final Class<?> clazz,
                                                                               final Class<A> annotation) {
        final var list = ClassUtil.getAllFields(clazz);
        list.removeIf(a -> a.getAnnotation(annotation) == null);
        return list;
    }

    public static Collection<Field> getAllNotStaticFields(Class<?> clazz) {
        final Collection<Field> list = getAllFields(clazz);
        list.removeIf(f -> isStatic(f.getModifiers()));
        return list;
    }

    public static <A extends Annotation, R> Set<R> getAnnotationParametr(Class<?> clazz,
                                                                         Class<A> annotation, Function<A, R> func) {
        final Set<R> res = new HashSet<>();
        for (var c : ClassUtil.getAllAnnotations(clazz, annotation))
            Collections.addAll(res, func.apply(c));
        return res;
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation, R> Set<R> getAnnotationParametrUnion(Class<?> clazz,
                                                                              Class<A> annotation) {
        return getAnnotationParametrUnion(clazz, annotation, a -> {
            try {
                return (R[]) annotation.getMethod("value").invoke(a);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                     | NoSuchMethodException | SecurityException e) {
                throw new WrappedException(e);
            }
        });
    }

    public static <A extends Annotation, R> Set<R> getAnnotationParametrUnion(Class<?> clazz,
                                                                              Class<A> annotation, Function<A, R[]> func) {
        final Set<R> res = new HashSet<>();
        for (var c : ClassUtil.getAllAnnotations(clazz, annotation))
            Collections.addAll(res, func.apply(c));
        return res;
    }

    @SuppressWarnings("unchecked")
    public static <T> Iterable<Class<? extends T>> getChildrens(Class<T> clazz,
                                                                Iterable<Class<?>> childrens) {
        List<Class<? extends T>> cl = new ArrayList<>();
        for (var c : childrens)
            if (clazz.isAssignableFrom(c))
                cl.add((Class<? extends T>) c);
        return cl;
    }

    public static <T, E> Set<Class<? extends E>> getClases(final Collection<T> iterable,
                                                           Function<T, E> function) {
        return getClases(iterable.parallelStream().map(function).collect(Collectors.toSet()));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<Class<? extends T>> getClases(final Collection<T> object) {
        return object.parallelStream().map(t -> (Class<? extends T>) t.getClass())
                .collect(Collectors.toSet());
    }

    public static <T> Set<Class<? extends T>> getClases(final Iterable<T> iterable) {
        Collection<T> list = new ArrayList<>();
        for (var var : iterable)
            list.add(var);
        return getClases(list);
    }

    public static <T, E> Set<Class<? extends E>> getClases(final Iterable<T> iterable,
                                                           Function<T, E> function) {
        Collection<E> list = new ArrayList<>();
        for (var var : iterable)
            list.add(function.apply(var));
        return getClases(list);
    }

    public static Object getDefault(Class<?> type) {
        if (type == int.class || type == Integer.class)
            return 0;
        if (type == short.class || type == Short.class)
            return (short) 0;
        if (type == boolean.class || type == Boolean.class)
            return false;
        if (type == double.class || type == Double.class)
            return 0.0d;
        if (type == float.class || type == Float.class)
            return 0.0f;
        if (type == char.class || type == Character.class)
            return (char) 0;
        if (type == byte.class || type == Byte.class)
            return (byte) 0;
        return null;
    }

    public static Object getField(Object object, Field field)
            throws IllegalArgumentException, IllegalAccessException {
        var accessible = field.canAccess(object);
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw e;
        } finally {
            field.setAccessible(accessible);
        }

    }

    public static Field getFields(Class<?> clazz, String field) {
        if (clazz == null)
            return null;
        try {
            var f = clazz.getDeclaredField(field);
            if (f != null)
                return f;
        } catch (NoSuchFieldException | SecurityException e) {
        }
        return getFields(clazz.getSuperclass(), field);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getNotPrimitive(Class<T> type) {
        if (type == float.class)
            return (Class<T>) Float.class;
        if (type == int.class)
            return (Class<T>) Integer.class;
        if (type == double.class)
            return (Class<T>) Double.class;
        if (type == char.class)
            return (Class<T>) Character.class;
        if (type == byte.class)
            return (Class<T>) Byte.class;
        if (type == short.class)
            return (Class<T>) Short.class;
        if (type == boolean.class)
            return (Class<T>) Boolean.class;
        if (type == long.class)
            return (Class<T>) Long.class;
        return type; // not primitive
    }

    public static List<Field> getSerializableFields(Class<?> clazz) {
        final var list = getAllFields(clazz);
        list.removeIf(SerializationVerifier::isSerializabeField);
        return list;
    }

    public static Class<?> hca(Iterable<Class<?>> classes) {
        var iter = classes.iterator();
        if (!iter.hasNext())
            return Object.class;
        Class<?> res = iter.next();
        while (res != null && iter.hasNext())
            res = hca(res, iter.next());
        return res;
    }

    public static Class<?> hca(Class<?> a, Class<?> b) {
        if (!a.isInterface() && !b.isInterface() || a == null || b == null)
            return null;
        throw new UnsupportedOperationException("this method mast full list of class");
    }

    public static boolean isExtends(Class<?> superClass, Class<?> clazz) {
        if (superClass == clazz)
            return true;
        return superClass.isAssignableFrom(clazz);
    }

    public static boolean isMutable(final Object obj) {
        if (obj == null)
            throw new IllegalArgumentException("obj is null, use isMutable(Object, Class)");
        final var c = obj.getClass();
        return isMutable(obj, c);
    }

    public static boolean isMutable(final Object obj, final Class<?> c) {
        if (c == null)
            throw new IllegalArgumentException("class is null");
        if (obj == null)
            return isMutableClass(c);
        if (c == String.class)
            return false;
        if (c.isArray())
            return getArrayLength(obj) > 0;
        for (var f : ClassUtil.getAllFields(c)) {
            final var m = f.getModifiers();
            final var t = f.getType();
            if (isStatic(m))
                continue;
            if (!isFinal(m))
                try {
                    return isMutable(f.get(obj));
                } catch (IllegalArgumentException | IllegalAccessException e1) {
                    return true;
                }
            try {
                final var v = f.get(obj);
                if (isMutable(v, t))
                    return true;
            } catch (Exception e) {
                if (isMutableClass(t))
                    return true;
            }
        }
        return false;
    }

    public static boolean isMutableClass(final Class<?> c) {
        if (c.isArray())
            return true;
        for (var f : ClassUtil.getAllFields(c)) {
            final var m = f.getModifiers();
            final var t = f.getType();
            if (isStatic(m))
                continue;
            if (!isFinal(m))
                return true;
            try {
                if (isMutableClass(t))
                    return true;
            } catch (StackOverflowError e) {
            }
        }
        return false;
    }

    public static boolean isOpen(Class<?> type) {
        return type.getModule().isOpen(type.getPackageName(), ClassUtil.class.getModule());
    }

    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type == Integer.class || type == Short.class
                || type == Boolean.class || type == Double.class || type == Float.class
                || type == Character.class || type == Byte.class;
    }

    public static Class<?> lca(Class<?> a, Class<?> b) {
        if (a == null || b == null)
            return Object.class;
        if (isExtends(b, a))
            return b;
        if (isExtends(a, b))
            return a;
        return lca(a.getSuperclass(), b);

    }

    public static Class<?> lca(Iterable<Class<?>> classes) {
        var iter = classes.iterator();
        if (!iter.hasNext())
            return Object.class;
        Class<?> res = iter.next();
        while (res != Object.class && iter.hasNext())
            res = lca(res, iter.next());
        return res;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<? super T> minParent(Class<T> clazz, Iterable<Class<?>> parents) {
        List<Class<? super T>> cl = new ArrayList<>();
        for (var c : parents)
            if (c.isAssignableFrom(clazz))
                cl.add((Class<? super T>) c);
        if (cl.isEmpty())
            return null;
        var minC = cl.get(0);
        var minD = extendsDepth(clazz, minC);
        for (var c : cl) {
            var d = extendsDepth(clazz, c);
            if (d < minD) {
                minD = d;
                minC = c;
            }
        }
        return minC;
    }

    public static int extendsDepth(Class<?> clazz, Class<?> superClass) {
        if (superClass.isInterface()) {
        } else {
            var res = 0;
            while (clazz != null) {
                if (superClass == clazz)
                    return res;
                clazz = clazz.getSuperclass();
                res++;
            }
        }
        return Integer.MAX_VALUE;
    }

    public static <T> T newInstance(Class<T> clazz, Object... args)
            throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Constructor<T> constructor = getConstructor(clazz, getClasses(args));
        var flag = constructor.canAccess(null);
        constructor.setAccessible(true);
        try {
            return constructor.newInstance(args);
        } catch (ExceptionInInitializerError e) {
            throw new WrappedException(e.getCause());
        } finally {
            constructor.setAccessible(flag);
        }
    }

    public static Object read(ObjectInput in, Class<?> cl) throws ClassNotFoundException,
            IOException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!isOpen(cl))
            return in.readObject();
        var fs = getAllFields(cl);
        fs.sort(Comparator.comparing(Field::getName));
        Object obj = newInstance(cl);
        for (Field f : fs) {
            if (isStatic(f.getModifiers()))
                continue;
            var fobj = read(in, f.getType());
            try {
                setField(obj, f, fobj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new IOException(e);
            }
        }
        return obj;
    }

    public static void setField(Object object, Field field, Object value)
            throws IllegalArgumentException, IllegalAccessException {
        var accessible = field.canAccess(object);
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw e;
        } finally {
            field.setAccessible(accessible);
        }
        field.setAccessible(accessible);
    }

    public static void write(Object t, ObjectOutput out) throws IOException {
        Class<?> cl = t.getClass();
        if (!isOpen(cl)) {
            out.writeObject(t);
            return;
        }
        var fs = getAllFields(cl);
        fs.sort(Comparator.comparing(Field::getName));
        for (Field f : fs) {
            if (isStatic(f.getModifiers()))
                continue;
            Object obj;
            try {
                obj = getField(t, f);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new IOException(e);
            }
            write(obj, out);
        }
    }

    private static int getArrayLength(Object obj) {
        try {
            return ((Object[]) obj).length;
        } catch (ClassCastException e) {
        }
        try {
            return ((int[]) obj).length;
        } catch (ClassCastException e) {
        }
        try {
            return ((short[]) obj).length;
        } catch (ClassCastException e) {
        }
        try {
            return ((float[]) obj).length;
        } catch (ClassCastException e) {
        }
        try {
            return ((double[]) obj).length;
        } catch (ClassCastException e) {
        }
        try {
            return ((boolean[]) obj).length;
        } catch (ClassCastException e) {
        }
        try {
            return ((char[]) obj).length;
        } catch (ClassCastException e) {
        }
        try {
            return ((byte[]) obj).length;
        } catch (ClassCastException e) {
        }
        throw new ClassCastException(obj + " is not array");
    }

    private static Class<?>[] getClasses(Object... objects) {
        var classes = new Class<?>[objects.length];
        for (var i = 0; i < objects.length; i++)
            if (objects[i] != null)
                classes[i] = objects[i].getClass();
        return classes;
    }

    @SuppressWarnings("unchecked")
    private static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>[] classes)
            throws NoSuchMethodException {
        final var len = classes.length;
        A:
        for (var c : clazz.getConstructors()) {
            if (c.getParameterCount() != len)
                continue A;
            final var params = c.getParameters();
            for (var i = 0; i < len; i++) {
                final var ci = classes[i];
                final var pi = params[i].getType();
                if (ci.isPrimitive()) {
                    if (ci != pi)
                        continue A;
                } else if (!isExtends(pi, ci))
                    continue A;
            }
            return (Constructor<T>) c;
        }
        throw new NoSuchMethodException("not found constructor " + clazz + "<init>"
                + Arrays.toString(classes) + " " + Arrays.toString(clazz.getConstructors()));
    }

}
