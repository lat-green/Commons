package com.greentree.commons.geometry.geom2d.collision.strategy;

import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import kotlin.Pair;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BMapCollisionStrategy extends AbstractCollisionStrategy {

    private static final SortCollisionStrategy SORT_COLLISION_STRATEGY = new SortCollisionStrategy();

    public BMapCollisionStrategy() {
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T extends Collidable2D> Collection<Pair<T, T>> getCollisionContact(T[] world) {
        final List<Pair<T, T>> res = new CopyOnWriteArrayList<>();
        final List<List<Set<Collidable2D>>> map = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            map.add(new CopyOnWriteArrayList<>());
        //			for(int j = 0; j < count; j++) {
        //				map.get(i).add(new HashSet<>());
        //			}
        Stream.iterate(0, n -> n < count * count, n -> n + 1).parallel().forEach(n -> {
            var a = map.get(n / count);
            a.add(new CopyOnWriteArraySet<>());
        });
        Arrays.asList(world).parallelStream().forEach(c -> {
            var aabb = c.getShape().getBoundingBox();
            int minx = (int) (aabb.getMinX() / size) + count / 2;
            int miny = (int) (aabb.getMinY() / size) + count / 2;
            int maxx = (int) (aabb.getMaxX() / size) + count / 2;
            int maxy = (int) (aabb.getMaxY() / size) + count / 2;
            for (int x = minx; x <= maxx; x++)
                for (int y = miny; y <= maxy; y++)
                    add(map, x, y, c);
        });
        //		for(var a : map) {
        //			for(var b : a) {
        //				System.out.printf("%2d ", b.size());
        //			}
        //			System.out.println();
        //		}
        Stream.iterate(0, n -> n < count * count, n -> n + 1).parallel().forEach(n -> {
            int x = n % count;
            int y = n / count;
            var a = get(map, x, y);
            if (a.isEmpty())
                return;
            T[] array = (T[]) new Collidable2D[a.size()];
            var a0 = SORT_COLLISION_STRATEGY.getCollisionContact(a.toArray(array));
            synchronized (res) {
                res.addAll(a0);
            }

        });
        //		int sum = 0;
        //		for(int i = 0; i < count; i++) {
        //			for(int j = 0; j < count; j++) {
        //				sum += map.get(i).get(j).size();
        //			}
        //		}
        return res.parallelStream().collect(Collectors.toList());
    }

    private void add(List<List<Set<Collidable2D>>> map, int x, int y, Collidable2D t) {
        if (x < 0 || x >= count || y < 0 || y >= count)
            return;
        var s = get(map, x, y);
        synchronized (s) {
            s.add(t);
        }
    }

    private <T> Set<T> get(List<List<Set<T>>> map, int i, int j) {
        return map.get(i).get(j);
    }

    private static final float max = 1000, min = -max, size = 40;

    private static final int count = (int) (((double) max - (double) min) / size);

}
