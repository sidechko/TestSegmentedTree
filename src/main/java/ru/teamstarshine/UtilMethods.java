package ru.teamstarshine;

public class UtilMethods {

    public static <T> T selectOneOfArgs(T o1, T o2) {
        if (o1 == null)
            return o2;
        return o1;
    }

    public static String growTreeFromString(String sapling) {
        StringBuilder sb = new StringBuilder(sapling);

        return sb.toString();
    }
}
