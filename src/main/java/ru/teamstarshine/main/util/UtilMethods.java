package ru.teamstarshine.main.util;

import ru.teamstarshine.main.tree.STAbstractNodeArea;

public class UtilMethods {
    public static <T> T selectOneOfArgs(T o1, T o2) {
        if (o1 == null)
            return o2;
        return o1;
    }

    public static void quickSort(int low, int high, STAbstractNodeArea[] array){
        System.out.printf("call quick sort %d %d", low, high);
        if (array.length == 0)
            return;
        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        STAbstractNodeArea basis = array[middle];
        int i = low, j = high;
        while (i <= j) {
            while (basis.condition(array[i], true))
                //>
                i++;
            while (basis.condition(array[i], false))
                //<
                j++;
            if (i <= j) {
                STAbstractNodeArea tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
                i++;
                j++;
            }
            if (low < j)
                quickSort(low, j, array);
            if (high > i)
                quickSort(i, high, array);
        }
    }

    public static void bubbleSort(STAbstractNodeArea[] area){
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area.length; j++) {
                if(i==j)continue;
                if(area[i].condition(area[j],true)){
                    STAbstractNodeArea tmp = area[i];
                    area[i] = area[j];
                    area[j] = tmp;
                    break;
                }
            }
        }
    }
}
