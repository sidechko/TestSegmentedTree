package ru.teamstarshine;

import com.sun.istack.internal.Nullable;

import java.util.Arrays;

public class Area {
    int[] pos1;
    int[] pos2;

    public Area(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.pos1 = new int[]{Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2)};
        this.pos2 = new int[]{Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2)};
    }

    public Area(Area a1, Area a2) {
        this(Math.min(a1.pos1[0], a2.pos1[0]),
                Math.min(a1.pos1[1], a2.pos1[1]),
                Math.min(a1.pos1[2], a2.pos1[2]),
                Math.max(a1.pos2[0], a2.pos2[0]),
                Math.max(a1.pos2[1], a2.pos2[1]),
                Math.max(a1.pos2[2], a2.pos2[2]));
    }

    public Area(int x, int y, int z){
        this(x,y,z,x,y,z);
    }

    public boolean isInside(int x, int y, int z) {
        return pos1[0] <= x && pos2[0] >= x && pos1[1] <= y && pos2[1] >= y && pos1[2] <= z && pos2[2] >= z;
    }

    public boolean isInside(Area area){
        return this.isInside(area.pos1[0],area.pos1[1],area.pos1[2]) && this.isInside(area.pos2[0],area.pos2[1],area.pos2[2]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Arrays.equals(pos1, area.pos1) && Arrays.equals(pos2, area.pos2);
    }

    public static Area sum(@Nullable Area a, @Nullable Area b) {
        return createNewByAreas(a,b);
    }

    private static Area createNewByAreas(Area a, Area b){
        if(a == null & b==null)
            return null;
        if(a == null)
            return b;
        else if( b == null)
            return a;
        else return new Area(a,b);
    }
}
