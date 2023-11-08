package test.com.greentree.commons.injector;

import java.util.Objects;

public class A {

    int x;
    int y;
    int z;

    public A(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        A a = (A) o;
        return x == a.x && y == a.y && z == a.z;
    }

    @Override
    public String toString() {
        return "A [" + x + ", " + y + ", " + z + "]";
    }

}
