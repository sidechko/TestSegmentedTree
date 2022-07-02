package ru.teamstarshine.two.fork;

import ru.teamstarshine.Area;
import ru.teamstarshine.UtilMethods;
import ru.teamstarshine.two.STAbstractNodeArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    public void leaveFromHome() {
        abaddon();
        listenChildAboutLeave(this);
        parent = null;
    }

    private void listenChildAboutLeave(STAbstractNodeArea child) {
        if (child == left)
            swapChild();
        right = null;
        if (left == null)
            leaveFromHome();
    }

    public void setLeftWithUpdate(STAbstractNodeArea left) {
        setLeft(left);
        setParentOf(left);
        updateAllWayTop();
    }

    public void setRightWithUpdate(STAbstractNodeArea right) {
        setRight(right);
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
        this.updateThisValue();
        if (parent == null)
            return;
        parent.updateThisValue();
        parent.updateAllWayTop();
    }

    private void updateThisValue() {
        value = sumInside();
        isFull = right != null && left != null;
        if (right instanceof STWayNode)
            isFull &= ((STWayNode) right).isFull;
        if (left instanceof STWayNode)
            isFull &= ((STWayNode) left).isFull;
        mutate = right instanceof STWayNode ^ left instanceof STWayNode;
        mutate |= right == null || left == null;
        isFull &= !mutate;
    }

    private Area sumInside(){
        if(left == null && right == null)
            return null;
        if(left == null)
            return Area.sum(null, right.value);
        if(right == null)
            return Area.sum(left.value, null);
        return Area.sum(left.value,right.value);
    }

    public STWayNode findNotFullChild() {
        if (isFull)
            return null;
        if (mutate){
            return this;
        }
        if (left instanceof STWayNode) {
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

    public boolean isChildless() {
        return right == null && left == null;
    }

    @Override
    public String toString() {
        String leftStr = "";
        String rightStr = "";

        if(left!=null)
            leftStr = left.toString();
        if(right!=null)
            rightStr = right.toString();

        List<String> leftSplit = Arrays.asList(leftStr.split("\n"));
        List<String> rightSplit =  Arrays.asList(rightStr.split("\n"));
        List<String> result = new ArrayList<>();
        int maxSize = 0;
        for (int i = 0; i < Math.max(leftSplit.size(),rightSplit.size()); i++) {
            String lPatr = i >= leftSplit.size() ? " " : leftSplit.get(i);
            String rPatr = i >= rightSplit.size() ? " " : rightSplit.get(i);
            result.add(lPatr+"  "+rPatr);
            maxSize = Math.max(result.get(i).length(), maxSize);
        }
        int finalMaxSize = maxSize;
        result = result.stream().map(str->{
            while (str.length()< finalMaxSize)
                str+=" ";
            return str;
        }).collect(Collectors.toList());
        StringBuilder toWN = new StringBuilder();
        for (int i = 0; i < maxSize; i++) {
            toWN.append(" ");
        }
        toWN.append('\n');
        toWN.replace(maxSize/2-1,maxSize/2+1,"Wn");
        result.forEach(str->toWN.append(str).append('\n'));
        toWN.delete(toWN.length()-1,toWN.length());
        return toWN.toString();
    }
    // ######wn######
    // **wn**##**wn**  **wn**
    // an**an##an**__  __**an

}
