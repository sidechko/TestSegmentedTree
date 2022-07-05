package ru.teamstarshine.main.tree;

import ru.teamstarshine.main.area.DefaultArea;
import ru.teamstarshine.main.util.UtilMethods;

import java.util.ArrayList;
import java.util.List;

public class TreeLogic {
    private STWayNode coreNode;

    public TreeLogic(DefaultArea[] list) {
        STAbstractNodeArea[] lastLayer = convertToBottomLayer(list);
        while (lastLayer.length > 1) {
            lastLayer = generateLayer(lastLayer);
        }
        coreNode = (STWayNode) lastLayer[0];
    }

    public TreeLogic(){
        coreNode = new STWayNode();
    }

    public void appendData(DefaultArea data) {
        STWayNode notFull = coreNode.findMostUnderNotFull();
        if (notFull == null) {
            STAbstractNodeArea lc = coreNode;
            int counter = 0;
            while (lc instanceof STWayNode) {
                counter++;
                lc = ((STWayNode) lc).getLeft();
            }
            lc = new STAbstractNodeArea(data);
            for (int i = 0; i < counter; i++) {
                lc = new STWayNode(lc, null);
            }
            coreNode = new STWayNode(coreNode, lc);
            return;
        }
        int leftCounter = 0;
        STAbstractNodeArea lc = coreNode;
        while (lc instanceof STWayNode) {
            leftCounter++;
            lc = ((STWayNode) lc).getLeft();
        }
        int rightCounter = 0;
        STWayNode rc = notFull;
        while (rc != null) {
            rightCounter++;
            rc = rc.getParent();
        }
        if (leftCounter > rightCounter) {
            STWayNode nodeToAdd = new STWayNode(new STAbstractNodeArea(data), null);
            for (int i = 1; i < leftCounter - rightCounter; i++) {
                nodeToAdd = new STWayNode(nodeToAdd, null);
            }
            notFull.setRightWithUpdate(nodeToAdd);
        } else {
            notFull.setRightWithUpdate(new STAbstractNodeArea(data));
        }
    }

    //QUERY
    public STAreaNodeInfo findAreaByCord(int x, int y, int z) {
        return findAreaByAreaStatsAtNode(new DefaultArea(x, y, z), coreNode, false);
    }

    public STAreaNodeInfo findNodeOfArea(DefaultArea area) {
        return findAreaByAreaStatsAtNode(area, coreNode, true);
    }

    public STAreaNodeInfo findNodeOfArea(DefaultArea area, boolean needCheck) {
        return findAreaByAreaStatsAtNode(area, coreNode, needCheck);
    }

    //FIND ALL AREA IN THIS CORDS
    public STAreaNodeInfo[] findNodesOfCords(int x, int y, int z) {
        List<STAreaNodeInfo> list = new ArrayList<>();
        findAreaByAreaStatsAtNodes(new DefaultArea(x, y, z), coreNode, list);
        return list.toArray(new STAreaNodeInfo[0]);
    }
    public STAreaNodeInfo[] findNodesOfCords(DefaultArea area) {
        List<STAreaNodeInfo> list = new ArrayList<>();
        findAreaByAreaStatsAtNodes(area, coreNode, list);
        return list.toArray(new STAreaNodeInfo[0]);
    }

    //GET ALL ABSTRACT NODE INFO
    private List<STAreaNodeInfo> getAllAbstractNodeInfo() {
        return coreNode.getAllAbstractNodeInfo(new ArrayList<>());
    }

    public void sort() {
        STAreaNodeInfo[] infoAbouts = getAllAbstractNodeInfo().toArray(new STAreaNodeInfo[0]);
        STAbstractNodeArea[] tmpSaveANA = new STAbstractNodeArea[infoAbouts.length];
        for (int i = 0; i < infoAbouts.length; i++) {
            tmpSaveANA[i] = infoAbouts[i].getNodeByInfo();
            System.out.printf("\n%s %s\n", infoAbouts[i], tmpSaveANA[i]);
        }
//        UtilMethods.bubbleSort(tmpSaveANA);
//        UtilMethods.quickSort(tmpSaveANA);

        for (int i = 0; i < infoAbouts.length; i++) {
            infoAbouts[i].setValueAtSide(tmpSaveANA[i]);
        }
        coreNode.updateAllWayDown();
    }

    //GET ALL AREAS (struct of tree cannot be change)
    public List<DefaultArea> getAllAreas() {
        return coreNode.getAllAreas(new ArrayList<>());
    }

    //REMOVE
    public boolean removeAbstractNode(int x, int y, int z) {
        return removeAbstractNode(new DefaultArea(x, y, z));
    }

    public boolean removeAbstractNode(DefaultArea area) {
        STAreaNodeInfo foundNode = findNodeOfArea(area, false);
        if (foundNode == null)
            return false;

        STWayNode parent = foundNode.getParent();

        if (!foundNode.isSide())
            parent.swapChild();
        parent.setRightWithUpdate(null);
        if(parent.isChildless())
            parent.leaveFromHome();

        if(coreNode.getRight() == null){
            if(coreNode.getLeft() instanceof STWayNode){
                coreNode = (STWayNode) coreNode.getLeft();
                coreNode.setGrand();
            }
            return true;
        }

        STWayNode mostRightWayNode = coreNode.getMostRight();


        if (mostRightWayNode.getRight() == null)
            mostRightWayNode.swapChild();

        parent.setRightWithUpdate(mostRightWayNode.getRight());
        mostRightWayNode.setRightWithUpdate(null);

        return true;
    }
    private STAreaNodeInfo findAreaByAreaStatsAtNode(DefaultArea area, STWayNode node, boolean needCheckEquals) {
        if (node.value.isInside(area)) {
            STAbstractNodeArea right = node.getRight();
            STAbstractNodeArea left = node.getLeft();
            STAreaNodeInfo leftInfo = null;
            STAreaNodeInfo rightInfo = null;
            first:
            if (!(right instanceof STWayNode)) {
                if (right == null)
                    break first;
                if (right.value.isInside(area)) {
                    if (needCheckEquals) {
                        if (area.equals(right.value))
                            rightInfo = new STAreaNodeInfo(node, true);
                        break first;
                    }
                    rightInfo = new STAreaNodeInfo(node, true);
                }
            } else {
                rightInfo = findAreaByAreaStatsAtNode(area, (STWayNode) right, needCheckEquals);
            }
            second:
            if (!(left instanceof STWayNode)) {
                if (left == null)
                    break second;
                if (left.value.isInside(area)) {
                    if (needCheckEquals) {
                        if (area.equals(left.value))
                            leftInfo = new STAreaNodeInfo(node, false);
                        break second;
                    }
                    leftInfo = new STAreaNodeInfo(node, false);
                }
            } else {
                leftInfo = findAreaByAreaStatsAtNode(area, (STWayNode) left, needCheckEquals);
            }
            return UtilMethods.selectOneOfArgs(leftInfo, rightInfo);
        }
        return null;
    }

    private void findAreaByAreaStatsAtNodes(DefaultArea area, STWayNode node, List<STAreaNodeInfo> list) {
        if (node.value.isInside(area)) {
            STAbstractNodeArea right = node.getRight();
            STAbstractNodeArea left = node.getLeft();
            if (!(right instanceof STWayNode)) {
                if(right!=null)
                    if (right.value.isInside(area))
                        list.add(new STAreaNodeInfo(node, true));

            } else {
                findAreaByAreaStatsAtNodes(area, (STWayNode) right, list);
            }
            if (!(left instanceof STWayNode)) {
                if(left!=null)
                    if (left.value.isInside(area))
                        list.add(new STAreaNodeInfo(node, false));

            } else {
                findAreaByAreaStatsAtNodes(area, (STWayNode) left, list);
            }
        }
    }

    //GENERATE
    private STWayNode[] generateLayer(STAbstractNodeArea[] nods) {
        STWayNode[] layer = new STWayNode[(int) Math.ceil(nods.length / 2D)];
        for (int i = 0; i < layer.length; i++) {
            if (i * 2 + 1 < nods.length)
                layer[i] = new STWayNode(nods[i * 2], nods[i * 2 + 1]);
            else
                layer[i] = new STWayNode(nods[i * 2], null);
        }
        return layer;
    }

    private STAbstractNodeArea[] convertToBottomLayer(DefaultArea[] data) {
        STAbstractNodeArea[] toRet = new STAbstractNodeArea[data.length];
        for (int i = 0; i < data.length; i++) {
            toRet[i] = new STAbstractNodeArea(data[i]);
        }
        return toRet;
    }

    public String getTreeOfString() {
        return coreNode.toString();
    }

}
