package com.greentree.commons.geometry.geom3d.operation;

import com.greentree.commons.geometry.geom3d.Collidable3D;
import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.geometry.geom3d.collision.CollisionEvent3D;
import com.greentree.commons.util.collection.Table;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class Shape3DBinaryOperations {

    private final static Shape3DBinaryOperation DEFAULT = new DefaultShape3DBinaryOperation<>();
    private final static Table<Class<? extends IShape3D>, Class<? extends IShape3D>, Shape3DBinaryOperation> table = new Table<>();

    static {
    }

    protected Shape3DBinaryOperations() {
    }

    public static <A extends IShape3D, B extends IShape3D> void add(Class<A> classA, Class<B> classB, Shape3DBinaryOperation<A, B> collisionHendler2D) {
        if (classA != classB)
            table.put(classB, classA, new SwapBinaryOperation3D<>(collisionHendler2D));
        table.put(classA, classB, collisionHendler2D);
    }

    public static <A extends IShape3D, B extends IShape3D> CollisionEvent3D<A, B> getCollisionEvent(A a, B b) {
        var sa = get(a);
        var sb = get(b);
        return new CollisionEvent3D<>(a, b, getHandler(sa, sb).getCollisionEvent(sa, sb));
    }

    private static IShape3D get(IShape3D a) {
        if (a instanceof Collidable3D) return ((Collidable3D) a).getShape();
        return a;
    }

    private static Shape3DBinaryOperation getHandler(IShape3D a, IShape3D b) {
        var res = table.get(a.getClass(), b.getClass());
        if (res != null) return res;
        return DEFAULT;
    }

    public static <A extends IShape3D, B extends IShape3D> boolean isIntersect(final A a, final B b) {
        var sa = get(a);
        var sb = get(b);
        return getHandler(sa, sb).isIntersect(sa, sb);
    }

}
