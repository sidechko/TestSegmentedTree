package ru.teamstarshine.main.tree;

import ru.teamstarshine.main.area.DefaultArea;

public class STAbstractNodeArea {
    static int COUNTER = 0;
    public DefaultArea value;
    public final int id;

    public STAbstractNodeArea(DefaultArea value) {
        id = COUNTER;
        COUNTER++;
        this.value = value;
    }

    public STAbstractNodeArea copy(){
        return new STAbstractNodeArea(this.value);
    }

    @Override
    public String toString() {
        return "An";
    }

    public boolean condition(STAbstractNodeArea other, boolean isFirstPos) {
        return value.condition(other.value, isFirstPos);
    }
}
