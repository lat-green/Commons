package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.geometry.geom2d.operation.*;
import com.greentree.commons.geometry.geom2d.shape.*;
import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil;
import com.greentree.commons.math.MathLine2D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.util.collection.Table;
import com.greentree.commons.util.cortege.Pair;
import com.greentree.commons.util.cortege.Triple;

import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class Shape2DUtil extends VectorGeometryUtil {

    private final static Table<Class<? extends Shape2D>, Class<? extends Shape2D>, Shape2DBinaryOperation> table = new Table<>();

    static {
        add(Circle2D.class, Circle2D.class, new CirclevsCircle());
        add(Circle2D.class, Rectangle2D.class, new CirclevsRectangle());
        add(Rectangle2D.class, Rectangle2D.class, new RectanglevsRectangle());
    }

    protected Shape2DUtil() {
    }

    public static Shape2D getMinkowskiAdd(FiniteShape2D a, FiniteShape2D b) {
        List<AbstractVector2f> list = new ArrayList<>();
        for (var p1 : a.getPoints())
            for (var p2 : b.getPoints()) {
                var point = new Vector2f(p1.x() + p2.x(), p1.y() + p2.y());
                if (!list.contains(point))
                    list.add(point);
            }
        return new Polygon2D(list);
    }

    public static <A extends Shape2D, B extends Shape2D> void add(Class<A> classA,
                                                                  Class<B> classB, Shape2DBinaryOperation<A, B> collisionHendler2D) {
        if (classA != classB)
            table.put(classB, classA, new SwapBinaryOperation2D<>(collisionHendler2D));
        table.put(classA, classB, collisionHendler2D);
    }

    public static AbstractVector2f contactLine(final Line2D line, final Line2D line_no_border) {
        final var p = Shape2DUtil.contact(line, line_no_border);
        final var max_x = Math.max(line.p1().x(), line.p2().x());
        final var min_x = line.p1().x() + line.p2().x() - max_x;
        final var max_y = Math.max(line.p1().y(), line.p2().y());
        final var min_y = line.p1().y() + line.p2().y() - max_y;
        if (p.x() > max_x || p.x() < min_x || p.y() > max_y || p.y() < min_y)
            return null;
        return p;
    }

    public static Vector2f contact(final Line2D a, final Line2D b) {
        return MathLine2D.contact(a.getMathLine(), a.getMathLine());
    }

    public static AbstractVector2f contactWithBorder(final Line2D a, final Line2D b) {
        final var res = contact(a, b);
        if (res == null || !a.getBoundingBox().isInside(res) || !b.getBoundingBox().isInside(res))
            return null;
        return res;
    }

    public static float distanse(final AbstractVector2f p, final Collection<? extends Shape2D> a) {
        float dis = Float.MAX_VALUE;
        for (final Shape2D l : a)
            dis = Math.min(dis, l.distance(p));
        return dis;
    }

    public static List<Integer> findAngles(Vector2f[] pointsList,
                                           Predicate<Triple<Vector2f, Vector2f, Vector2f>> predicate) {
        var points = cycle(pointsList, 2);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < points.length - 2; i++) {
            var t = new Triple<>(points[i], points[i + 1], points[i + 2]);
            if (predicate.test(t))
                res.add(i);
        }
        return res;
    }

    public static final <T extends Collidable2D> Collection<CollisionEvent2D<T, T>> getCollisions(
            Collection<Pair<T, T>> contacts) {
        Collection<CollisionEvent2D<T, T>> res = new ArrayList<>(contacts.size());
        contacts.parallelStream().forEach(pair -> {
            final var event = Shape2DUtil.getCollisionEvent(pair.first, pair.seconde);
            synchronized (event) {
                res.add(event);
            }
        });
        return res;
    }

    public static <A extends Collidable2D, B extends Collidable2D> CollisionEvent2D<A, B> getCollisionEvent(
            A a, B b) {
        final var sa = a.getShape();
        final var sb = b.getShape();
        return new CollisionEvent2D<>(a, b, getHandler(sa, sb).getCollisionEvent(sa, sb));
    }

    private static Shape2DBinaryOperation getHandler(Shape2D a, Shape2D b) {
        return getHandler(a.getClass(), b.getClass());
    }

    private static Shape2DBinaryOperation getHandler(Class<? extends Shape2D> a,
                                                     Class<? extends Shape2D> b) {
        var res = table.get(a, b);
        if (res != null)
            return res;
        return Shape2DBinaryOperation.DEFAULT;
    }

    public static <A extends Shape2D, B extends Shape2D> Collection<AbstractVector2f> getContactPoint(
            A a, B b) {
        return getHandler(a, b).getContactPoint(a, b);
    }

    public static <A extends Shape2D, B extends Shape2D> boolean isIntersect(final A a,
                                                                             final B b) {
        return getHandler(a, b).isIntersect(a, b);
    }

    @Deprecated
    public static Polygon2D toClockwisePoligon(AbstractVector2f... collection) {
        var c = getCenter(collection);
        List<AbstractVector2f> list = new ArrayList<>(collection.length);
        Collections.addAll(list, collection);
        Collections.sort(list, Comparator.comparingDouble(p -> {
            float cos = p.x() - c.x();
            float sin = p.y() - c.y();
            float len = cos * cos + sin * sin;
            if (len < 1E-9)
                return 0;
            len = Mathf.sqrt(len);
            cos /= len;
            sin /= len;
            float ang = Mathf.acos(cos);
            if (sin < 0)
                ang *= -1;
            return ang;
        }));
        return new Polygon2D(list);
    }

}
