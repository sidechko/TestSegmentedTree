package ru.teamstarshine.main.util;

import ru.teamstarshine.main.area.DefaultArea;
import ru.teamstarshine.main.tree.STAbstractNodeArea;

public class UtilMethods {
    public static <T> T selectOneOfArgs(T o1, T o2) {
        if (o1 == null)
            return o2;
        return o1;
    }

//    public static void quickSort(STAbstractNodeArea[] array) {
//        quickSort(0, array.length - 1, array, array.length - 1, 0);
//    }

//    private static void quickSort(int low, int high, STAbstractNodeArea[] array,int lastLow, int lastHigh){
//        if (array.length == 0)
//            return;
//        if(low == lastLow && high == lastHigh)
//            return;
//        if (low >= high)
//            return;
//        int middle = low + (high - low) / 2;
//        STAbstractNodeArea basis = array[middle];
//        int i = low, j = high;
//        while (i <= j) {
//            while (basis.condition(array[i]))
//                i++;
//            while (!basis.condition(array[j]))
//                j--;
//            if (i <= j) {
//                STAbstractNodeArea tmp = array[i];
//                array[i] = array[j];
//                array[j] = tmp;
//                i++;
//                j--;
//            }
//            if (low < j)
//                quickSort(low, j, array, low, high);
//            if (high > i)
//                quickSort(i, high, array, low, high);
//        }
//    }

    public static void bubbleSort(STAbstractNodeArea[] area) {
        int count = 0;
        for (int i = 0; i < area.length - 1; i++) {
            DefaultArea o1 = area[i].value;
            DefaultArea o2 = area[i + 1].value;
            if (!o1.condition(o2))
                continue;
            System.out.println("start");
            STAbstractNodeArea tmp = area[i];
            area[i] = area[i + 1];
            area[i + 1] = tmp;
            count++;

        }
        if (count > 0) {
            bubbleSort(area);
        }
    }
}
