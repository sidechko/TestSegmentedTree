package ru.teamstarshine.main;

import ru.teamstarshine.main.area.DefaultArea;
import ru.teamstarshine.main.area.UserArea;
import ru.teamstarshine.main.tree.STAreaNodeInfo;
import ru.teamstarshine.main.tree.TreeLogic;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainTwoForkOne {
    static final int SIZE = 2;
    static DefaultArea[] areas = new DefaultArea[SIZE];
    static Random rnd = new Random();

//    FULL WORK QUERY, REMOVE, APPEND
//    TODO SORT
    public static void main(String[] args) {
        for (int i = 1; i < SIZE+1; i++) {
            areas[i-1] = randomArea();
        }

        shuffle(SIZE*64);

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
    public static void shuffle(int countOfShuffle){
        for (int i = 0; i < countOfShuffle; i++) {
            int pos1 = rnd.nextInt(SIZE);
            int pos2 = rnd.nextInt(SIZE);
            DefaultArea tmp = areas[pos1];
            areas[pos1] = areas[pos2];
            areas[pos2] = tmp;
        }
    }
}
