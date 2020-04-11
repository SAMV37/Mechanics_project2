package com.samv3l;

import java.util.ArrayList;

class Utils {

    private static ArrayList swap(ArrayList<Double> arr, int a, int b) {
        double temp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, temp);

        return arr;
    }

    protected static ArrayList sort(ArrayList<Double> arr) {
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.size() - i - 1; j++) {
                if (arr.get(j) > arr.get(j + 1)) {
                    swap(arr, j, j + 1);
                }
            }
        }
        return arr;
    }

    public static void duplicateTimeRemoveSorted(ArrayList<Double> arr) {
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).equals(arr.get(i - 1))) {
                i--;
                arr.remove(i);
            }
        }
    }
}