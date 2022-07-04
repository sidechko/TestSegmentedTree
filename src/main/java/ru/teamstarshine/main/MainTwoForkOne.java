package ru.teamstarshine.main;

import ru.teamstarshine.main.area.DefaultArea;
import ru.teamstarshine.main.area.UserArea;
import ru.teamstarshine.main.tree.STAreaNodeInfo;
import ru.teamstarshine.main.tree.TreeLogic;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainTwoForkOne {
    static final int SIZE = 6;
    static DefaultArea[] areas = new DefaultArea[SIZE];
    static Random rnd = new Random();
    public static void main(String[] args) {
        for (int i = 1; i < SIZE+1; i++) {
            areas[i-1] = new DefaultArea(i*100,i*100,i*100,i*200,i*200,i*200);
        }

        shuffle(SIZE*64);

        TreeLogic treeLogic = new TreeLogic(areas);

        System.out.println("test generate");
        System.out.println(treeLogic.getTreeOfString());
        treeLogic.getAllAreas().forEach(System.out::println);
        System.out.println();

        System.out.println("test remove");
        treeLogic.removeAbstractNode(111,111,111);
        System.out.println(treeLogic.getTreeOfString());
        treeLogic.getAllAreas().forEach(System.out::println);
        System.out.println();

        for (int i = 1; i < 5; i++) {
            System.out.println("test append "+i);
            treeLogic.appendData(new DefaultArea(5*i,5*i,5*i,10*i,10*i,10*i));
            System.out.println(treeLogic.getTreeOfString());
            treeLogic.getAllAreas().forEach(System.out::println);
            System.out.println();
        }

        System.out.println("test sort");
        treeLogic.sort();
        System.out.println(treeLogic.getTreeOfString());
        treeLogic.getAllAreas().forEach(System.out::println);
        System.out.println();

        System.out.println("test equals");
        System.out.println((new DefaultArea(1,1,1)).equals(
                new UserArea(UUID.randomUUID(),1,1,1,1,1,1,"TEST")));
        System.out.println();
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
