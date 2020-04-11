package com.samv3l;

import java.util.Scanner;

public class InputHelper {
    public static double getFraction(int n) {
        Scanner s = new Scanner(System.in);
        System.out.print("Friction for object " + n + " here ---> ");
        double inp = s.nextDouble();
        if (inp < 0 || inp > 0.5) {
            System.out.println("Try again");
            return getFraction(n);
        }
        return inp;
    }

    public static double getMass(int n) {
        Scanner s = new Scanner(System.in);
        System.out.print("Mass for object " + n + " here ---> ");
        double inp = s.nextDouble();
        if (inp < 0 || inp > 10) {
            System.out.println("Try again");
            return getMass(n);
        }
        return inp;
    }

}
