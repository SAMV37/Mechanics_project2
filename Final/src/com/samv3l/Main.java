package com.samv3l;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    ////////////////In this section I create variables, that I will use in this code
    private static final int gravity = 10;
    private static final int v0 = 0;
    private static double mass1, mass2, mass3;
    private static double friction1, friction2, friction3;
    private static double x1, y1, x2, x3, y3;
    private static double acc2, acc3;
    private static double rope;
    private static double ropeH;

    private static int timeCheck = 0;
    private static int m1Checker = 0;

    private static final ArrayList<Double> times = new ArrayList<>();

    private static final ArrayList<Double> velocities = new ArrayList<>();

    private static final ArrayList<Double> forces = new ArrayList<>();
    private static final ArrayList<Integer> forceChangesTimes = new ArrayList<>();

    private static final ArrayList<Double> force1 = new ArrayList<>();
    private static final ArrayList<Double> acc1 = new ArrayList<>();
    private static final ArrayList<Double> displacementByTimeOfM1 = new ArrayList<>();

    private static final ArrayList<Double> x1P = new ArrayList<>();
    private static final ArrayList<Double> x2P = new ArrayList<>();
    private static final ArrayList<Double> x3P = new ArrayList<>();
    private static final ArrayList<Double> y3P = new ArrayList<>();


    static Scanner scanner = new Scanner(System.in);

    ////////////////In this section I get positions from user
    private static void getInitialPositionsFromUser(){
        //x1, y1
        System.out.print("Initial position for x1 here ---> ");
        x1 = scanner.nextDouble();

        System.out.print("Initial position for y1 here ---> ");
        y1 = scanner.nextDouble();

        //x2 (Using separate method to check if position is correct)
        getInitialPosition2();

        //x3, y3 (Using separate method to check if position is correct)
        getInitialPosition3();

        calculateRopeHeights();
    }

    private static void getInitialPosition2() {
        System.out.print("Initial position for x2 here ---> ");
        double input = scanner.nextDouble();
        if (input >= x1) {
            System.out.println("Try again");
            getInitialPosition2();
        }
        x2 = input;
    }

    private static void getInitialPosition3() {
        System.out.print("Initial position for x3 here ---> ");
        x3 = scanner.nextDouble();

        System.out.print("Initial position for y3 here ---> ");
        double input = scanner.nextDouble();
        if (input > y1) {
            System.out.println("Try again");
            getInitialPosition3();
        }
        y3 = input;
    }



    ////////////////In this section I get fractions from user
    private static void getFractionsFromUser(){
        friction1 = InputHelper.getFraction(1);
        friction2 = InputHelper.getFraction(2);
        friction3 = InputHelper.getFraction(3);

        getInitialPositionsFromUser();
    }


    ////////////////In this section I get mass values from user
    private static void getMassFromUser(){
        mass1 = InputHelper.getMass(1);
        mass2 = InputHelper.getMass(2);
        mass3 = InputHelper.getMass(3);

        getFractionsFromUser();
    }

    private static void insertTime() {
        System.out.print("New time number here (-1 if you do not want to change) ---> ");
        double input = scanner.nextDouble();
        if(input != -1){
            if(input >= 0){
                times.add(input);
                System.out.println("Time added");
            }else{
                System.out.println("Try again");
            }
            insertTime();
        }
    }

    private static void force() {

        if (forces.size() == 0) {
            System.out.print("Force of F here ---> ");
            double inp = scanner.nextDouble();
            forces.add(inp);
            forceChangesTimes.add(0);
        }
        System.out.println("Change force at a given time? (y/n)");
        String answer = scanner.next();
        if (answer.equals("y")) {
            System.out.print("When to change force ---> ");
            int timeInput = scanner.nextInt();

            if (timeInput < 0 || timeInput <= timeCheck) {
                System.out.println("Try again.");
                force();
            }
            timeCheck = timeInput;

            System.out.println("Amount of force here ---> ");
            double forceInput = scanner.nextDouble();
            forceChangesTimes.add(timeInput);
            forces.add(forceInput);
            force();
        }

        for (double force : forces) {
            force1.add(force * friction1);
        }
    }




    private static void calculateRopeHeights() {
        ropeH = x1 - x2;
        rope = (x1 - x2) + (y1 - y3);

        force();
    }

    private static void calculateAcc() {
        double initial;
        double a, d;
        int t;

        for (double f : force1) {
            acc1.add(f / (mass1 + mass2 + mass3));
        }

        acc2 = (friction2 * mass2 * gravity) / ((friction1 * mass1) + (friction2 * mass2));

        acc3 = (friction2 * mass2 * gravity) / ((friction1 * mass1) + (friction2 * mass2));

        velocities.add((double) v0);

        for (int i = 1; i < forces.size(); i++) {
            a = acc1.get(i);
            t = forceChangesTimes.get(i);
            if(t == 0){
                velocities.add((double) 0);
                continue;
            }

            d = (velocities.get(m1Checker) * forceChangesTimes.get(i)) + ((0.5) * (Math.pow(forceChangesTimes.get(i), 2)) * (acc1.get(m1Checker)));
            m1Checker++;
            displacementByTimeOfM1.add(d);

            initial = (d / t) - ((0.5) * (a) * (t));
            velocities.add(initial);
        }
    }

    private static double displacementOfM1(double time) {
        int f = -1;

        for (Integer forceChangesTime : forceChangesTimes) {
            if (time >= forceChangesTime) {
                f++;
                continue;
            }
            if (time < forceChangesTime) {
                break;
            }
        }

        return (velocities.get(f) * time) + ((0.5) * (Math.pow(time, 2)) * (acc1.get(f)));
    }


    private static double displacementOfM3(double time) {
        return Math.min((v0 * time) + ((0.5) * (Math.pow(time, 2)) * (acc3)), ropeH);
    }


    /////////Calculate final positions
    private static void positionsCalculate() {
        for (Double time : times) {
            x1P.add(x1 - displacementOfM1(time));
            x2P.add(x2 + displacementOfM3(time) - displacementOfM1(time));
            x3P.add(x3 - displacementOfM1(time));
            y3P.add(y3 - displacementOfM3(time));
        }

        printResults();
    }


    public static void printResults() {


        /////Here I print all masses
        System.out.println("The mass of object 1: " + mass1 + " kg");
        System.out.println("The mass of object 2: " + mass2 + " kg");
        System.out.println("The mass of object 3: " + mass3 + " kg");

        System.out.println("-----------------------------------");

        /////Here I print all frictions
        System.out.println("The friction of object 1: " + friction1);
        System.out.println("The friction of object 2: " + friction2);
        System.out.println("The friction of object 3: " + friction3);

        System.out.println("-----------------------------------");


        /////Here I print times
        System.out.println("The given times are:");
        //0 second
        int t0 = 0;
        System.out.println("t0: " + t0);
        for (int i = 0; i < times.size(); i++) {
            System.out.println("t"+(i + 1) + ": " + times.get(i) + " seconds");
        }


        System.out.println("-----------------------------------");


        System.out.println("The length of the rope is: " + rope + "m");


        System.out.println("-----------------------------------");


        System.out.println("Times -> " + times);
        System.out.println("Velocities -> " + velocities);
        System.out.println("Forces -> " + forces);
        System.out.println("ForceChangesTimes -> " + forceChangesTimes);
        System.out.println("Force1 -> " + force1);
        System.out.println("Acc1 -> " + acc1);
        System.out.println("X1 position -> " + x1P);
        System.out.println("X2 position -> " + x2P);
        System.out.println("X3 position -> " + x3P);
        System.out.println("Y3 position -> " + y3P);
    }


    public static void main(String[] args) {
        getMassFromUser();

        calculateAcc();

        insertTime();
        Utils.sort(times);
        Utils.duplicateTimeRemoveSorted(times);


        positionsCalculate();
    }
}