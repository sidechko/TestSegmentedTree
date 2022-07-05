package ru.teamstarshine.main.area;


import java.util.Arrays;

public class DefaultArea {
    int id;
    int[] pos1;
    int[] pos2;

    public DefaultArea(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.pos1 = new int[]{Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2)};
        this.pos2 = new int[]{Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2)};
    }

    public DefaultArea(DefaultArea a1, DefaultArea a2) {
        this(Math.min(a1.pos1[0], a2.pos1[0]),
                Math.min(a1.pos1[1], a2.pos1[1]),
                Math.min(a1.pos1[2], a2.pos1[2]),
                Math.max(a1.pos2[0], a2.pos2[0]),
                Math.max(a1.pos2[1], a2.pos2[1]),
                Math.max(a1.pos2[2], a2.pos2[2]));
    }

    public DefaultArea(int x, int y, int z) {
        this(x, y, z, x, y, z);
    }

    public boolean isInside(int x, int y, int z) {
        return pos1[0] <= x && pos2[0] >= x && pos1[1] <= y && pos2[1] >= y && pos1[2] <= z && pos2[2] >= z;
    }

    public boolean isInside(DefaultArea area) {
        return this.isInside(area.pos1[0], area.pos1[1], area.pos1[2]) && this.isInside(area.pos2[0], area.pos2[1], area.pos2[2]);
    }

    public boolean furtherThan(DefaultArea area) {
        int x = pos1[0];
        int y = pos1[1];
        int z = pos1[2];
        int x1 = area.pos1[0];
        int y1 = area.pos1[1];
        int z1 = area.pos1[2];
        if (x > x1)
            return true;
        if (y > y1)
            return true;
        return z > z1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultArea area = (DefaultArea) o;
        return equals(area);
    }

    private boolean equals(int[] pos1, int[] pos2) {
        return Arrays.equals(this.pos1, pos1) && Arrays.equals(this.pos2, pos2);
    }

    public boolean equals(DefaultArea area) {
        return equals(area.pos1, area.pos2);
    }

    public static DefaultArea sum(DefaultArea a, DefaultArea b) {
        return createNewByAreas(a, b);
    }

    private static DefaultArea createNewByAreas(DefaultArea a, DefaultArea b) {
        if (a == null & b == null)
            return null;
        if (a == null)
            return b;
        else if (b == null)
            return a;
        else return new DefaultArea(a, b);
    }

    @Override
    public String toString() {
        return "Area{" +
                "pos1=" + Arrays.toString(pos1) +
                ", pos2=" + Arrays.toString(pos2) +
                '}';
    }

    public boolean condition(DefaultArea other) {
        if (pos1[0] > other.pos1[0]) return true;
        if (pos1[1] > other.pos1[1]) return true;
        return pos1[2] > other.pos1[2];
    }
}
