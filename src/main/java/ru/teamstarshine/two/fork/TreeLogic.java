package ru.teamstarshine.two.fork;

import ru.teamstarshine.Area;
import ru.teamstarshine.UtilMethods;
import ru.teamstarshine.two.STAbstractNodeArea;

public class TreeLogic {
    STWayNode coreNode;

    public TreeLogic(Area[] list) {
        STAbstractNodeArea[] lastLayer = convertToBottomLayer(list);
        while (lastLayer.length > 1) {
            lastLayer = generateLayer(lastLayer);
        }
        coreNode = (STWayNode) lastLayer[0];
    }

    //APPEND NEW AREA
    public void appendData(Area data) {
        STAbstractNodeArea newData = new STAbstractNodeArea(data);
        STWayNode notFull = coreNode.findNotFullChild();
        if (notFull == null) {
            coreNode = new STWayNode(coreNode, newData);
        } else {
            STAbstractNodeArea nodeToMove = notFull.getRight();
            if (nodeToMove != null)
                notFull.setRightWithUpdate(new STWayNode(nodeToMove, null));
            notFull.setRightWithUpdate(newData);
        }
    }

    //QUERY
    public STAreaNodeInfo findAreaByCord(int x, int y, int z) {
        return findAreaByAreaStatsAtNode(new Area(x, y, z), coreNode, false);
    }

    public STAreaNodeInfo findNodeOfArea(Area area) {
        return findAreaByAreaStatsAtNode(area, coreNode, true);
    }

    //REMOVE
    public boolean removeAbstractNode(int x, int y, int z) {
        return removeAbstractNode(new Area(x, y, z));
    }

    public boolean removeAbstractNode(Area area) {
        STAreaNodeInfo foundNode = findNodeOfArea(area);
        if (foundNode == null)
            return false;

        STWayNode parent = foundNode.getParent();
        if (!foundNode.isSide())
            parent.swapChild();
        parent.setRight(null);

        STWayNode mostRightWayNode = getMostRight();
        if(mostRightWayNode.getRight() == null)
            mostRightWayNode.swapChild();
        parent.setRightWithUpdate(mostRightWayNode.getRight());
        if(mostRightWayNode.isChildless())
            mostRightWayNode.leaveFromHome();

        return true;
    }

    private STWayNode getMostRight(){
        STWayNode node = coreNode;
        while (node.getRight() instanceof STWayNode)
            node = (STWayNode) node.getRight();
        return node;
    }

    private STAreaNodeInfo findAreaByAreaStatsAtNode(Area area, STWayNode node, boolean needCheckEquals) {
        if (node.value.isInside(area)) {
            STAbstractNodeArea right = node.getRight();
            STAbstractNodeArea left = node.getLeft();
            if (!(right instanceof STWayNode)) {
                if (right.value.isInside(area)) {
                    if (needCheckEquals)
                        return area.equals(right.value) ? new STAreaNodeInfo(node, true) : null;
                    return new STAreaNodeInfo(node, true);
                }
                return null;
            }
            if (!(left instanceof STWayNode)) {
                if (left.value.isInside(area)) {
                    if (needCheckEquals)
                        return area.equals(left.value) ? new STAreaNodeInfo(node, false) : null;
                    return new STAreaNodeInfo(node, false);
                }
                return null;
            }
            return UtilMethods.selectOneOfArgs(
                    findAreaByAreaStatsAtNode(area, (STWayNode) right, needCheckEquals),
                    findAreaByAreaStatsAtNode(area, (STWayNode) left, needCheckEquals)
            );
        }
        return null;
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

    private STAbstractNodeArea[] convertToBottomLayer(Area[] data) {
        STAbstractNodeArea[] toRet = new STAbstractNodeArea[data.length];
        for (int i = 0; i < data.length; i++) {
            toRet[i] = new STAbstractNodeArea(data[i]);
        }
        return toRet;
    }

}
