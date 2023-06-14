package com.greentree.commons.reflection.info;

import com.greentree.commons.reflection.ClassUtil;
import com.greentree.commons.util.function.AbstractSaveFunction;

import java.lang.reflect.Type;
import java.util.Objects;

public final class ClassInfo<C> implements TypeInfo<C> {

    private final static ClassInfoSingletonFactory INSTANCIES = new ClassInfoSingletonFactory();

    private static final long serialVersionUID = 1L;
    private final Class<C> cls;

    private ClassInfo(Class<C> cls) {
        this.cls = cls;
    }

    public static <C> ClassInfo<C> get(Class<C> cls) {
        return INSTANCIES.get(cls);
    }

    @Override
    public TypeInfo<?>[] getTypeArguments() {
        return new TypeInfo[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public TypeInfo<? super C>[] getInterfaces() {
        final var is = cls.getGenericInterfaces();
        final TypeInfo<? super C>[] ts = new TypeInfo[is.length];
        for (var i = 0; i < is.length; i++)
            ts[i] = TypeInfoBuilder.getTypeInfo(is[i]);
        return ts;
    }

    @Override
    public TypeInfo<C> getBoxing() {
        if (isPrimitive())
            return INSTANCIES.get(ClassUtil.getNotPrimitive(cls));
        return this;
    }

    @Override
    public TypeInfo<? super C> getSuperType() {
        final var super_class = cls.getGenericSuperclass();
        if (super_class == null)
            return null;
        return TypeInfoBuilder.getTypeInfo(super_class);
    }

    @Override
    public String getSimpleName() {
        return cls.getSimpleName();
    }

    @Override
    public String getName() {
        return cls.getName();
    }

    @Override
    public boolean isPrimitive() {
        return cls.isPrimitive();
    }

    @Override
    public Class<C> toClass() {
        return cls;
    }

    @Override
    public boolean isSuperOf(TypeInfo<? super C> superType) {
        if (superType instanceof ClassInfo)
            return superType.toClass().isAssignableFrom(cls);
        return superType.isSuperTo(this);
    }

    @Override
    public boolean isSuperTo(TypeInfo<? extends C> type) {
        if (type instanceof ClassInfo)
            return cls.isAssignableFrom(type.toClass());
        return type.isSuperOf(this);
    }

    @Override
    public CharSequence getTypeName() {
        return cls.getTypeName();
    }

    @Override
    public Type getType() {
        return cls;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cls);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ClassInfo<?> other = (ClassInfo<?>) obj;
        return Objects.equals(cls, other.cls);
    }

    @Override
    public String toString() {
        return cls.toString();
    }

    private static final class ClassInfoSingletonFactory
            extends AbstractSaveFunction<Class<?>, ClassInfo<?>> {

        private static final long serialVersionUID = 1L;

        @SuppressWarnings("unchecked")
        public <C> ClassInfo<C> get(Class<C> cls) {
            return (ClassInfo<C>) applyRaw(cls);
        }

        @Override
        protected ClassInfo<?> create(Class<?> cls) {
            return new ClassInfo<>(cls);
        }

    }

}
