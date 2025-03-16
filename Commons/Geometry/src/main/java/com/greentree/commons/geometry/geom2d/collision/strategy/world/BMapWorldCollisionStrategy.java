package com.greentree.commons.geometry.geom2d.collision.strategy.world;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.geometry.geom2d.shape.Rectangle2D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import kotlin.Pair;

import java.util.*;

public class BMapWorldCollisionStrategy<T extends Collidable2D> extends WorldCollisionStrategy<T> {

    private static final Map<Collidable2D, Rectangle2D> aabb_cache = new FunctionAutoGenerateMap<>(
            c -> c.getShape().getBoundingBox());

    public final float max, min, size;

    public final int count;
    private final List<List<Collection<Collidable2D>>> map;

    private final Map<Collidable2D, AABBi> removeTable = new HashMap<>();
    private final Map<Collidable2D, AABBi> aabbi_cache = new FunctionAutoGenerateMap<>(
            s -> new AABBi(aabb_cache.get(s)));

    private final float DELTHA;

    public BMapWorldCollisionStrategy() {
        this(1000, 100);
    }

    public BMapWorldCollisionStrategy(float area, float size) {
        max = area;
        min = -area;
        this.size = size;
        count = (int) (((double) max - (double) min) / size);
        DELTHA = count / 2f * size;
        map = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            map.add(new ArrayList<>(count));
            for (int j = 0; j < count; j++)
                map.get(i).add(new HashSet<>() {

                    private static final long serialVersionUID = 1L;

                    public boolean equals(Object o) {
                        return o == this;
                    }
                });
        }
    }

    public String list() {
        return removeTable.keySet().toString();
    }

    public int size() {
        return removeTable.size();
    }

    private class AABBi {

        public final int minx, maxx, miny, maxy;

        public AABBi(Rectangle2D aabb) {
            minx = (int) Mathf.clamp(0, count - 1, (int) ((aabb.getMinX() + DELTHA) / size));
            maxx = (int) Mathf.clamp(0, count - 1, (int) ((aabb.getMaxX() + DELTHA) / size));
            miny = (int) Mathf.clamp(0, count - 1, (int) ((aabb.getMinY() + DELTHA) / size));
            maxy = (int) Mathf.clamp(0, count - 1, (int) ((aabb.getMaxY() + DELTHA) / size));
        }

        public int size() {
            return (maxx - minx + 1) * (maxy - miny + 1);
        }

    }

    @Override
    protected void add0(final Collidable2D shape) {
        AABBi aabb = aabbi_cache.get(shape);
        if (aabb.maxx < 0 || aabb.minx > count || aabb.maxy < 0 || aabb.miny > count)
            return;
        synchronized (removeTable) {
            removeTable.put(shape, aabb);
        }
        //		var fposs = poss;
        //
        //		Stream.iterate(0, k -> k <= (maxx - minx + 1)*(maxy - miny + 1), k -> k+1).forEach(k -> {
        //
        //			final int x = k/(maxy - miny + 1);
        //			final int y = k%(maxy - miny + 1);
        //
        //			add(shape, get0(x, y), fposs);
        //
        //		});
        //		Stream.iterate(minx, x -> x <= maxx, x -> x+1).forEach(x ->
        //			Stream.iterate(miny, y -> y <= maxy, y -> y+1).forEach(y ->
        //				add(x, y, shape))
        //		);
        for (int x = aabb.minx; x <= aabb.maxx; x++) {
            var xx = map.get(x);
            for (int y = aabb.miny; y <= aabb.maxy; y++) {
                var s = xx.get(y);
                synchronized (s) {
                    s.add(shape);
                }
            }
        }
    }

    @Override
    public Collection<? extends Pair<T, T>> getCollisionContact() {
        final Collection<Pair<T, T>> res = new HashSet<>();
        for (int x = 0; x < count; x++)
            for (int y = 0; y < count; y++) {
                var a = get0(x, y);
                if (a.size() < 2)
                    continue;
                Collidable2D[] array = new Collidable2D[a.size()];
                Collection<Pair<T, T>> a0 = getCollisionContact(a.toArray(array));
                if (a0.isEmpty())
                    continue;
                synchronized (res) {
                    res.addAll(a0);
                }
            }
        return res;
    }

    private Collection<Collidable2D> get0(int x, int y) {
        return map.get(x).get(y);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Collidable2D> Collection<Pair<T, T>> getCollisionContact(
            Collidable2D... world) {
        final var res = new ArrayList<Pair<T, T>>();
        Arrays.sort(world, Comparator.comparing(s -> aabb_cache.get(s).getMinX()));
        Rectangle2D[] aabbs = new Rectangle2D[world.length];
        for (int i = world.length - 1; i >= 0; i--) {
            T a = (T) world[i];
            var aAABB = aabb_cache.get(a);
            aabbs[i] = aAABB;
            for (int j = i + 1; j < world.length; j++) {
                T b = (T) world[j];
                var bAABB = aabbs[j];
                if (aAABB.getMaxX() < bAABB.getMinX())
                    break;
                if (Shape2DUtil.isIntersect(a.getShape(), b.getShape()))
                    res.add(new Pair<>(a, b));
            }
        }
        return res;
    }

    @Override
    protected void remove0(Collidable2D shape) {
        aabbi_cache.remove(shape);
        aabb_cache.remove(shape);
        remove00(shape);
    }

    protected void remove00(final Collidable2D shape) {
        var aabb = removeTable.remove(shape);
        if (aabb == null)
            return;
        for (int x = aabb.minx; x <= aabb.maxx; x++)
            for (int y = aabb.miny; y <= aabb.maxy; y++) {
                var s = get0(x, y);
                synchronized (s) {
                    s.remove(shape);
                }
            }
    }
    //	@Override
    //	protected void reset0(T shape) {
    //		aabbi_cache.reset(shape);
    //		remove00(shape);
    //		add0(shape);
    //	}

}
