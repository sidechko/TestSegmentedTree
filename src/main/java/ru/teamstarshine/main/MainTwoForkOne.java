package ru.teamstarshine.main;

import ru.teamstarshine.main.area.DefaultArea;
import ru.teamstarshine.main.area.UserArea;
import ru.teamstarshine.main.tree.STAreaNodeInfo;
import ru.teamstarshine.main.tree.TreeLogic;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class MainTwoForkOne {
    static final int SIZE = 2;
    static DefaultArea[] areas = new DefaultArea[SIZE];
    static Random rnd = new Random();

//    FULL WORK QUERY, REMOVE, APPEND
//    TODO SORT
    public static void main(String[] args) {
        speedTest1();
    }

    public static void speedTest1(){
        int size = 1048576;
        boolean needPrintAreas = false;
        DefaultArea areas[] = new DefaultArea[size];
        for (int i = 0; i < size; i++) {
            areas[i] = randomArea();
        }
        shuffle(size*64,areas);
        DefaultArea valueToFind = areas[rnd.nextInt(size)];
        int x = valueToFind.getX1()+1;
        int y = valueToFind.getY1()+1;
        int z = valueToFind.getZ1()+1;
        System.out.println("value to find: "+valueToFind);
        TreeLogic treeLogic = new TreeLogic(areas);
        System.out.println();

        System.out.println("query by cord start time  "+System.currentTimeMillis());
        STAreaNodeInfo info = treeLogic.findAreaByCord(x,y,z);
        System.out.println("query by cord finish time "+System.currentTimeMillis());
        System.out.println("is equals "+info.getNodeByInfo().value.equals(valueToFind));
        System.out.println();

        System.out.println("query by cord areas start time  "+System.currentTimeMillis());
        STAreaNodeInfo[] info1 = treeLogic.findNodesOfCords(x,y,z);
        System.out.println("query bu cord areas finish time "+System.currentTimeMillis()+"\n");
        if(needPrintAreas) Arrays.stream(info1).forEach(i-> System.out.print(i.getNodeByInfo().value+" "));
        System.out.println();

        System.out.println("query by area start time  "+System.currentTimeMillis());
        STAreaNodeInfo info2 = treeLogic.findNodeOfArea(valueToFind);
        System.out.println("query by area finish time "+System.currentTimeMillis());
        System.out.println("is equals "+info2.getNodeByInfo().value.equals(valueToFind));
        System.out.println();

        System.out.println("query by area with check start time  "+System.currentTimeMillis());
        STAreaNodeInfo info3 = treeLogic.findNodeOfArea(valueToFind,true);
        System.out.println("query by area with check finish time "+System.currentTimeMillis());
        if(needPrintAreas) System.out.println("is equals "+info3.getNodeByInfo().value.equals(valueToFind));
        System.out.println();

        System.out.println("query by area areas start time  "+System.currentTimeMillis());
        STAreaNodeInfo[] info4 = treeLogic.findNodesOfCords(x,y,z);
        System.out.println("query bu area areas finish time "+System.currentTimeMillis()+"\n");
        if(needPrintAreas) Arrays.stream(info4).forEach(i-> System.out.print(i.getNodeByInfo().value+" "));
        System.out.println();
    }
    public static void fullTest1(){

        for (int i = 1; i < SIZE+1; i++) {
            areas[i-1] = randomArea();
        }

        shuffle(SIZE*64,areas);

        TreeLogic treeLogic = new TreeLogic(areas);

        System.out.println("test generate");
        System.out.println(treeLogic.getTreeOfString());
        treeLogic.getAllAreas().forEach(System.out::println);
        System.out.println();

//        System.out.println("test remove");
//        treeLogic.removeAbstractNode(111,111,111);
//        System.out.println(treeLogic.getTreeOfString());
//        treeLogic.getAllAreas().forEach(System.out::println);
//        System.out.println();

//        for (int i = 1; i < SIZE+1; i++) {
//            System.out.println("test append "+i);
//            treeLogic.appendData(new DefaultArea(5*i,5*i,5*i,10*i,10*i,10*i));
//            System.out.println(treeLogic.getTreeOfString());
//            treeLogic.getAllAreas().forEach(System.out::println);
//            System.out.println();
//        }
        treeLogic.appendData(new DefaultArea(5,5,5,10,10,10));
        System.out.println(treeLogic.getTreeOfString());
        treeLogic.removeAbstractNode(6,6,6);
        System.out.println(treeLogic.getTreeOfString());
        treeLogic.appendData(new DefaultArea(5,5,5,10,10,10));
        System.out.println(treeLogic.getTreeOfString());

//        System.out.println("test sort");
//        treeLogic.sort();
//        System.out.println(treeLogic.getTreeOfString());
//        treeLogic.getAllAreas().forEach(System.out::println);
//        System.out.println();

        System.out.println("test equals");
        System.out.println((new DefaultArea(1,1,1)).equals(
                new UserArea(UUID.randomUUID(),1,1,1,1,1,1,"TEST")));
        System.out.println();
    }

    public static DefaultArea randomArea(){
        int x1 = rnd.nextInt(10000) * (rnd.nextBoolean()? 1 : -1);
        int y1 = rnd.nextInt(255);
        int z1 = rnd.nextInt(10000) * (rnd.nextBoolean()? 1 : -1);
        int x2 = rnd.nextInt(10000) * (rnd.nextBoolean()? 1 : -1);
        int y2 = rnd.nextInt(255);
        int z2 = rnd.nextInt(10000) * (rnd.nextBoolean()? 1 : -1);
        return new DefaultArea(x1,y1,z1,x2,y2,z2);
    }
    public static void shuffle(int countOfShuffle, DefaultArea[] array){
        for (int i = 0; i < countOfShuffle; i++) {
            int pos1 = rnd.nextInt(array.length);
            int pos2 = rnd.nextInt(array.length);
            DefaultArea tmp = array[pos1];
            array[pos1] = array[pos2];
            array[pos2] = tmp;
        }
    }
}
