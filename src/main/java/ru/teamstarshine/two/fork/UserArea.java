package ru.teamstarshine.two.fork;

import ru.teamstarshine.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserArea extends Area {
    public final String owner;
    public final UUID uuid;
    private final List<String> builders = new ArrayList<>();

    public UserArea(UUID areaUUID, int x1, int y1, int z1, int x2, int y2, int z2, String owner) {
        super(x1, y1, z1, x2, y2, z2);
        this.owner = owner;
        this.uuid = areaUUID;
        builders.add(owner);
    }

    public String getOwner() {
        return owner;
    }

    public String[] getBuilders() {
        return builders.toArray(new String[0]);
    }

    public boolean appendBuilder(String builder){
        if(builders.contains(builder))
            return false;
        return builders.add(builder);
    }

    public boolean removeBuilder(String builder){
        return builders.remove(builder);
    }
}
