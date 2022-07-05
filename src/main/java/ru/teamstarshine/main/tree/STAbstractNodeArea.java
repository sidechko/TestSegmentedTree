package ru.teamstarshine.main.tree;

import ru.teamstarshine.main.area.DefaultArea;

public class STAbstractNodeArea {
    public DefaultArea value;

    public STAbstractNodeArea(DefaultArea value) {
        this.value = value;
    }

    public STAbstractNodeArea copy(){
        return new STAbstractNodeArea(this.value);
    }

    @Override
    public String toString() {
        return "An";
    }

    public boolean condition(STAbstractNodeArea other) {
        return value.condition(other.value);
    }
}
