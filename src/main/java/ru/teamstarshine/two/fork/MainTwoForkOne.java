package ru.teamstarshine.two.fork;

import ru.teamstarshine.Area;
import ru.teamstarshine.UtilMethods;
import ru.teamstarshine.two.STAbstractNodeArea;

import java.util.Random;

public class MainTwoForkOne {
    static final int SIZE = 4;
    static Area[] areas = new Area[SIZE];
    static Random rnd = new Random();
    public static void main(String[] args) {
        for (int i = 1; i < SIZE+1; i++) {
            areas[i-1] = new Area(i*100,i*100,i*100,i*200,i*200,i*200);
        }
        shuffle(SIZE*64);
        TreeLogic treeLogic = new TreeLogic(areas);
        System.out.println("test print");
        System.out.println(UtilMethods.growTreeFromString(treeLogic.getTreeOfString()));
        System.out.println();

        System.out.println("test query");
        STAreaNodeInfo ab = treeLogic.findAreaByCord(111,111,111);
        System.out.println(ab.getArea().toString());
        System.out.println();

        System.out.println("test remove");
        boolean bc = treeLogic.removeAbstractNode(111,111,111);
        System.out.println(bc);
        System.out.println(UtilMethods.growTreeFromString(treeLogic.getTreeOfString()));
        System.out.println();

        System.out.println("test append");
        treeLogic.appendData(new Area(100,100,100,200,200,200));
//        treeLogic.appendData(new Area(50,50,50,100,100,100));
        System.out.println(UtilMethods.growTreeFromString(treeLogic.getTreeOfString()));
    }
    public static void shuffle(int countOfShuffle){
        for (int i = 0; i < countOfShuffle; i++) {
            int pos1 = rnd.nextInt(SIZE);
            int pos2 = rnd.nextInt(SIZE);
            Area tmp = areas[pos1];
            areas[pos1] = areas[pos2];
            areas[pos2] = tmp;
        }
    }
}
