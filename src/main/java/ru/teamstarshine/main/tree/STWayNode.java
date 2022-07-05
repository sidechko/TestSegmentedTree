package ru.teamstarshine.main.tree;

import ru.teamstarshine.main.area.DefaultArea;
import ru.teamstarshine.main.util.UtilMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class STWayNode extends STAbstractNodeArea {
    private STWayNode parent;
    private STAbstractNodeArea left;
    private STAbstractNodeArea right;
    private boolean isFull = false;

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
        parent.listenChildAboutLeave(this);
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
    protected void updateAllWayDown() {
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
        if (right instanceof STWayNode) isFull &= ((STWayNode) right).isFull;
        if (left instanceof STWayNode) isFull &= ((STWayNode) left).isFull;
    }

    public STWayNode findMostUnderNotFull(){
        if(isFull)
            return null;
        boolean lisway = left instanceof STWayNode;
        boolean risway = right instanceof STWayNode;
        STWayNode leftValue = lisway ?  ((STWayNode) left).findMostUnderNotFull() : this;
        STWayNode rightValue = risway ?  ((STWayNode) right).findMostUnderNotFull() : this;
        return UtilMethods.selectOneOfArgs(leftValue, rightValue);

    }

    protected void setGrand(){
        this.parent = null;
    }

    private DefaultArea sumInside() {
        if (left == null && right == null)
            return null;
        if (left == null)
            return DefaultArea.sum(null, right.value);
        if (right == null)
            return DefaultArea.sum(left.value, null);
        return DefaultArea.sum(left.value, right.value);
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

    public boolean isChildless(){
        return left==null&&right==null;
    }
    public List<DefaultArea> getAllAreas(List<DefaultArea> list) {
        if (left instanceof STWayNode)
            ((STWayNode) left).getAllAreas(list);
        else if (left != null)
            list.add(left.value);
        if (right instanceof STWayNode)
            ((STWayNode) right).getAllAreas(list);
        else if (right != null)
            list.add(right.value);
        return list;
    }

    protected STWayNode getMostRight() {
        if (right instanceof STWayNode)
            return ((STWayNode) right).getMostRight();
        if (right == null)
            if (left instanceof STWayNode)
                return ((STWayNode) left).getMostRight();
        return this;
    }

    protected List<STAreaNodeInfo> getAllAbstractNodeInfo(List<STAreaNodeInfo> list) {
        if (left instanceof STWayNode)
            ((STWayNode) left).getAllAbstractNodeInfo(list);
        else
            if(left != null)
                list.add(new STAreaNodeInfo(this, false));
        if (right instanceof STWayNode)
            ((STWayNode) right).getAllAbstractNodeInfo(list);
        else
            if(right!=null)
                list.add(new STAreaNodeInfo(this, true));
        return list;
    }

    @Override
    public String toString() {
        String leftStr = "";
        String rightStr = "";

        if (left != null)
            leftStr = left.toString();
        if (right != null)
            rightStr = right.toString();

        List<String> leftSplit = Arrays.asList(leftStr.split("\n"));
        List<String> rightSplit = Arrays.asList(rightStr.split("\n"));
        List<String> result = new ArrayList<>();
        int maxSize = 0;
        for (int i = 0; i < Math.max(leftSplit.size(), rightSplit.size()); i++) {
            String lPatr = i >= leftSplit.size() ? " " : leftSplit.get(i);
            String rPatr = i >= rightSplit.size() ? " " : rightSplit.get(i);
            result.add(lPatr + "   " + rPatr);
            maxSize = Math.max(result.get(i).length(), maxSize);
        }
        int finalMaxSize = maxSize;
        result = result.stream().map(str -> {
            while (str.length() < finalMaxSize)
                str += " ";
            return str;
        }).collect(Collectors.toList());
        StringBuilder toWN = new StringBuilder();
        for (int i = 0; i < maxSize; i++) {
            toWN.append(" ");
        }
        toWN.append('\n');
        toWN.replace(maxSize / 2 - 1, maxSize / 2 + 1, "Wn");
        result.forEach(str -> toWN.append(str).append('\n'));
        toWN.delete(toWN.length() - 1, toWN.length());
        return toWN.toString();
    }

}
