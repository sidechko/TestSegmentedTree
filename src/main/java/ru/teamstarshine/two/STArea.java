package ru.teamstarshine.two;

import ru.teamstarshine.Area;

import java.util.ArrayList;
import java.util.List;

public class STArea {
    Area[] data;
    STNodeArea coreNode;

    public STArea(Area[] data) {
        this.data = data.clone();
    }

    //APPEND DATA
    private boolean rightIsFullFromNode(STNodeArea fromNode) {
        STAbstractNodeArea node = fromNode;
        while (node instanceof STNodeArea) {
            if (((STNodeArea) node).right == null)
                return false;
            node = ((STNodeArea) node).right;
        }
        return true;
    }

    private boolean rightAndLeftSizeEqualsFromNode(STNodeArea fromNode) {
        int leftCounter = 0;
        STAbstractNodeArea node = fromNode;
        while (node instanceof STNodeArea) {
            if (((STNodeArea) node).right == null)
                break;
            node = ((STNodeArea) node).right;
            leftCounter++;
        }
        node = fromNode;
        while (node instanceof STNodeArea) {
            if (((STNodeArea) node).left == null)
                break;
            node = ((STNodeArea) node).left;
            leftCounter--;
        }
        return leftCounter == 0;
    }

    private STNodeArea createNewAndMoveDown(STAbstractNodeArea moveble) {
        return new STNodeArea(moveble.copy(), null);
    }

    private STNodeArea appendOnFullAndReturnNew(STAbstractNodeArea fullTree, STAbstractNodeArea data) {
        return createNewAndMoveDown(fullTree).setRight(data).updateValue();
    }

    private void appendNewNoteAtNode(STNodeArea node, STAbstractNodeArea data) {
        if (rightIsFullFromNode(node)) {
            if (rightAndLeftSizeEqualsFromNode(node)) {
                node.setAttr(appendOnFullAndReturnNew(node, data));
            } else {
                if (!(node.left instanceof STNodeArea)) {
                    node.setLeft(createNewAndMoveDown(node.left));
                } else {
                    appendNewNoteAtNode((STNodeArea) node.left, data);
                    node.updateValueVoid();
                }
            }
        }
    }

    private void appendData(STAbstractNodeArea data) {
        appendNewNoteAtNode(coreNode, data);
    }

    public void appendArea(Area area) {
        Area[] newData = new Area[this.data.length+1];
        System.arraycopy(this.data,0,newData,0,this.data.length);
        newData[this.data.length] = area;
        this.data = newData;
        appendData(new STAbstractNodeArea(area));
    }


    //QUERY

    public Area[] findAreaWithCordInside(int x, int y, int z) {
        List<Area> list = new ArrayList<>();
        findAreaWithCordInside(coreNode, x, y, z, list);
        return list.toArray(new Area[list.size()]);
    }

    private void findAreaWithCordInside(STAbstractNodeArea node, int x, int y, int z, List<Area> listToRet) {
        if (!node.value.isInside(x, y, z))
            return;
        if (node instanceof STNodeArea) {
            findAreaWithCordInside(((STNodeArea) node).left, x, y, z, listToRet);
            findAreaWithCordInside(((STNodeArea) node).right, x, y, z, listToRet);
        } else {
            listToRet.add(node.value);
        }
    }

    private STNodeArea findMostRightAbstParent(){
        STNodeArea parent = coreNode;
        if(parent == null)
            return null;
        while (parent.right instanceof STNodeArea){
            parent = (STNodeArea) parent.right;
        }
        return parent;
    }

    private boolean removeNodeWithThisValue(STAbstractNodeArea node, Area value, STNodeArea parent) {
        if (!(node instanceof STNodeArea)){
            boolean isEql = node.value.equals(value);
            if(parent != null){
                if(parent.left == node)
                    parent.swapChildPos();
                parent.right = null;
            }
            moveNodeRightChildYoNodeRightWay(findMostRightAbstParent(), parent);

            return isEql;
        }
        if (!node.value.isInside(value))
            return false;
        if (!removeNodeWithThisValue(((STNodeArea) node).right, value, (STNodeArea) node) &&
            !removeNodeWithThisValue(((STNodeArea) node).left, value, (STNodeArea) node))
            return false;
        ((STNodeArea) node).updateValueVoid();

        return true;
    }

    private void removeMostRightWayIfNull(){
        STNodeArea node = coreNode;
        List<STNodeArea> nodeToKill = new ArrayList<>();
        while (node.right instanceof STNodeArea){
            node = (STNodeArea) node.right;
            nodeToKill.add(node);
        }
        STNodeArea[] nods = nodeToKill.toArray(new STNodeArea[0]);
        for (int i = nods.length-1; i > 0; i++) {
            if(!nods[i].childIsNull())
                break;
            nods[i-1].setRight(null);
        }

    }

    private void moveNodeRightChildYoNodeRightWay(STNodeArea moveNodeParent, STNodeArea stepFather) {
        if(moveNodeParent == null)
            return;
        if(stepFather == null)
            return;
        stepFather.setRight(moveNodeParent.right);
        moveNodeParent.right = null;
    }

    // УДАЛЕНИЕ
    public void removeValueFromTree(Area value){
        removeNodeWithThisValue(coreNode,value,null);
        removeMostRightWayIfNull();
    }

    // SORT


//    Мыслил как сделать добавление

//    public void appendNewNode(STAbstractNodeArea node){
//        if(rightIsFullFromCore()){
//            if(rightAndLeftSizeEqualsFromCore()){
//                coreNode = appendOnFullAndReturnNew(coreNode, node);
//                return;
//            }else{
//                if(!(coreNode.left instanceof STNodeArea)){
//                    coreNode.left = createNewAndMoveDown(coreNode.left);
//                }else {
//                    if(rightAndLeftSizeEqualsFromNode((STNodeArea) coreNode.left)){
//                        if(rightIsFullFromNode((STNodeArea) coreNode.left)){
//                            if(rightAndLeftSizeEqualsFromNode((STNodeArea) coreNode.left)){
//                                coreNode.left = appendOnFullAndReturnNew(coreNode.left, node);
//                                return;
//                            }else{
//
//                            }
//                        }else
//                    }
//                }
//            }
//        }
//
//    }


//  Первые попытки создания логики генерации и добавления данных

//    public void appendDate(Area ... newData){
//        Area[] update = new Area[newData.length+this.data.length];
//        System.arraycopy(data,0,update,0,data.length);
//        System.arraycopy(newData,0,update,data.length,update.length);
//        this.data = update;
//    }
//
//    private void generate(){
//        STAbstractNodeArea[] layer = generateNodeFromDate();
//        while (layer.length>1){
//            layer = generateLayer(layer);
//        }
//        coreNode = (STNodeArea) layer[0];
//    }
//
//    private STNodeArea[] generateLayer(STAbstractNodeArea[] underLayerDate){
//        STNodeArea[] layer = new STNodeArea[((int)Math.ceil(underLayerDate.length/2D))];
//        for (int i = 0; i < underLayerDate.length; i++) {
//            STNodeArea node = layer[i];
//            if(node==null){
//                layer[i] = new STNodeArea(null);
//                layer[i].left = underLayerDate[i];
//            }
//            else {
//                layer[i].right = underLayerDate[i];
//                layer[i].updateValue();
//            }
//        }
//        return layer;
//    }
//
//    private STAbstractNodeArea[] generateNodeFromDate(){
//        STAbstractNodeArea[] ret = new STAbstractNodeArea[data.length];
//        for (int i = 0; i < data.length; i++) {
//            ret[i] = new STAbstractNodeArea(data[i]);
//        }
//        return ret;
//    }
}
