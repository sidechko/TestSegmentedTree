package ru.teamstarshine.main.tree;

import ru.teamstarshine.main.area.DefaultArea;

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

    public DefaultArea getArea(){
        return getNodeByInfo().value;
    }

    public STWayNode getParent() {
        return parent;
    }

    public boolean isSide() {
        return side;
    }

    protected void setValueAtSide(STAbstractNodeArea value){
        if(!side){
            parent.setLeft(value);
        }
        else {
            parent.setRight(value);
        }
    }
}
