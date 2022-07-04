package ru.teamstarshine.deprecated.one;

import java.util.ArrayList;
import java.util.List;

public class MainOne {
    public static void main(String[] args) {
        List<Double> weight = new ArrayList<>();
        for (int i = -500; i <= 500; i++) {
            for (int j = -500; j <= 500; j++) {
                weight.add((double) (i+j));
            }
        }
        for (int i = 0; i < 1000000; i++) {
            if(i!=0 && i%1000==0)
                System.out.print("\n");
            System.out.print(weight.get(i)+" ");
        }
    }
    public static int[] appendValueAtPos(int[] basis, int pos, int value){
        int[] areasTMP = new int[basis.length+1];
        System.arraycopy(basis, 0, areasTMP, 0, pos);
        areasTMP[pos]=value;
        System.arraycopy(basis,pos,areasTMP,pos+1,basis.length-pos);
        return areasTMP;
    }

    public static int findPosMegjdDvuh(int[] arr,int value){
        if(value < arr[0])
            return 0;
        if(value > arr[arr.length-1])
            return arr.length;
        int midPos = (arr.length)/2;
        int move = midPos;
        while(true){
            move /= 2;
            int rightValue = arr[midPos];
            int leftValue = arr[midPos+1];
            if(rightValue < value && leftValue > value)
                return midPos+1;
            if(rightValue < value && leftValue < value)
                midPos = midPos + move;
            else if(rightValue > value && leftValue > value)
                midPos = midPos - move;
        }
    }
}