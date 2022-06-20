package ru.teamstarshine.two;

import ru.teamstarshine.Area;

public class STAbstractNodeArea {
    Area value;

    public STAbstractNodeArea(Area value) {
        this.value = value;
    }

    public STAbstractNodeArea copy(){
        return new STAbstractNodeArea(this.value);
    }
}
