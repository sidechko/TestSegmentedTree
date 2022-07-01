package ru.teamstarshine.two.fork;

import ru.teamstarshine.Area;
import ru.teamstarshine.UtilMethods;
import ru.teamstarshine.two.STAbstractNodeArea;

import java.util.Random;

public class STWayNode extends STAbstractNodeArea {
    private static final Random RfPIDtL = new Random();
    private STWayNode parent;
    private STAbstractNodeArea left;
    private STAbstractNodeArea right;
    private boolean isFull = false;
    private boolean mutate = false;

    //CONSTRUCTORS
    public STWayNode(STWayNode parent, STAbstractNodeArea left, STAbstractNodeArea right) {
        super(null);
        setLeft(left);
        setParentOf(left);

        setRight(right);
        setParentOf(right);

        setParent(parent);
        updateThisValue();
    }

    public STWayNode(STAbstractNodeArea left, STAbstractNodeArea right) {
        this(null, left, right);
    }

    public STWayNode() {
        this(null, null);
    }

    //SETTERS TIER 2
    
    public void leaveFromHome(){
        abaddon();
        listenChildAboutLeave(this);
        parent = null;
    }
    
    private void listenChildAboutLeave(STAbstractNodeArea child){
        if(child == left)
            swapChild();
        right = null;
        if(left == null)
            leaveFromHome();
    }

    public void setLeftWithUpdate(STAbstractNodeArea left) {
        setLeft(left);
        setParentOf(left);
        updateAllWayTop();
    }

    public void setRightWithUpdate(STAbstractNodeArea right) {
        setLeft(right);
        setParentOf(right);
        updateAllWayTop();
    }

    private void setParentOf(STAbstractNodeArea nodeToSetParent) {
        if (nodeToSetParent instanceof STWayNode)
            ((STWayNode) nodeToSetParent).setParent(this);
    }

    //UPDATERS
    private void updateAllWayDown() {
        if (left instanceof STWayNode)
            ((STWayNode) left).updateAllWayDown();
        if (right instanceof STWayNode)
            ((STWayNode) right).updateAllWayDown();
        updateThisValue();
    }

    private void updateAllWayTop() {
        if(parent == null)
            return;
        parent.updateThisValue();
        parent.updateAllWayTop();
    }

    private void updateThisValue() {
        value = Area.sum(left.value, right.value);
        isFull = right !=null && left != null;
        if(right instanceof STWayNode)
            isFull &= ((STWayNode) right).isFull;
        if(left instanceof STWayNode)
            isFull &= ((STWayNode) left).isFull;
        mutate = right instanceof STWayNode ^ left instanceof STWayNode;
        isFull &= !mutate;
    }

    public STWayNode findNotFullChild(){
        if(isFull)
            return null;
        if(mutate)
            return this;
        if(left instanceof STWayNode){
            return UtilMethods.selectOneOfArgs(((STWayNode) left).findNotFullChild(), ((STWayNode) right).findNotFullChild());
        }
        return this;
    }

    //SETTERS

    private void abaddon() {
        left = null;
        right = null;
    }

    private void setParent(STWayNode node) {
        parent = node;
    }

    public void setLeft(STAbstractNodeArea left) {
        this.left = left;
    }

    public void setRight(STAbstractNodeArea right) {
        this.right = right;
    }

    public void swapChild() {
        STAbstractNodeArea tmp = left;
        left = right;
        right = tmp;
    }

    //GETTERS

    public STWayNode getParent() {
        return parent;
    }

    public STAbstractNodeArea getLeft() {
        return left;
    }

    public STAbstractNodeArea getRight() {
        return right;
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isMutate() {
        return mutate;
    }

    public boolean isChildless(){
        return right == null && left == null;
    }

    @Override
    public String toString() {
        int rndRes = RfPIDtL.nextInt(10);
        return "\nWn"+rndRes+":{\n\t"+"("+rndRes+")"+left.toString()+",\n\t("+rndRes+")"+right.toString()+"\b\b\b\b}";
    }
}
