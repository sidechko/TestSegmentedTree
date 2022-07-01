package ru.teamstarshine.two.fork;

import ru.teamstarshine.Area;
import ru.teamstarshine.two.STAbstractNodeArea;

public class STAreaNodeInfo {
    private final STWayNode parent;
    private final boolean side;

    public STAreaNodeInfo(STWayNode parent, boolean side){
        this.side = side;
        this.parent = parent;
    }

    public STAbstractNodeArea getNodeByInfo(){
        return side ? parent.getRight() : parent.getLeft();
    }

    public Area getArea(){
        return getNodeByInfo().value;
    }

    public STWayNode getParent() {
        return parent;
    }

    public boolean isSide() {
        return side;
    }
}
