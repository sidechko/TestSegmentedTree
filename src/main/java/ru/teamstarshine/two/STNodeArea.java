package ru.teamstarshine.two;

import ru.teamstarshine.Area;

public class STNodeArea extends STAbstractNodeArea{
    STAbstractNodeArea left = null;
    STAbstractNodeArea right = null;

    public STNodeArea(STAbstractNodeArea left, STAbstractNodeArea right){
        super(Area.sum(left.value,right.value));
        this.left = left;
        this.right = right;
    }

    public STNodeArea setLeft(STAbstractNodeArea left){
        this.left = left;
        return this;
    }

    public STNodeArea setRight(STAbstractNodeArea right){
        this.right = right;
        return this;
    }

    public void updateValueVoid(){
        this.value = Area.sum(left.value,right.value);
    }

    public STNodeArea updateValue(){
        this.value = Area.sum(left.value,right.value);
        return this;
    }

    public void setAttr(STNodeArea stna){
        this.left = stna.left;
        this.right = stna.right;
        this.value = stna.value;
    }

    public boolean childIsNull(){
        return this.left == null && this.right == null;
    }

    @Override
    public STAbstractNodeArea copy() {
        return new STNodeArea(this.left,this.right);
    }
}
