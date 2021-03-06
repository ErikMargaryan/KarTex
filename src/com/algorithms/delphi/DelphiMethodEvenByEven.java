package com.algorithms.delphi;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Delphi's method
 *
 * @author Erik Margaryan
 * @version 2.4
 * @since 02.05.2021
 */

public class DelphiMethodEvenByEven {

    static Scanner input = new Scanner(System.in);

    public int K;
    public int N;
    public int C;
    public int[][] matrix;
    public String[] questions;
    public float midValue = 0;
    static List<String> listString;
    static List<Integer> listInt;
    int[] groupRates;
    public float[] differenceToMidValue;
    float S = 0;

    public DelphiMethodEvenByEven(int K, int N, int C) {
        this.K = K;
        this.N = N;
        this.C = C;
        this.matrix = new int[C][C];
        this.questions = new String[K];
        listString = new ArrayList<>();
        listInt = new ArrayList<>();
        groupRates = new int[K];
        this.differenceToMidValue = new float[K];
    }

    //Դիտարկվող օբյեկտներ
    public void questions() {
        questions[0] = "1) Դասախոսի գիտելիքները․ ";
        questions[1] = "2) Աշխատանքային հեռանկար․ ";
        questions[2] = "3) Լսարանային պայմանները․ ";
        questions[3] = "4) Մատուցման ձևը․ ";
        int r = 2;
        int n = questions.length;
        printCombination(questions, n, r);
        System.out.println(Arrays.toString(questions));
    }

    static void combinationUtil(String[] questions, String[] data, int start,
                                int end, int index, int r) {

        if (index == r) {
            IntStream.range(0, r).forEach(j -> {
                System.out.print(data[j] + " ");
                listString.add(data[j]);
            });
            System.out.println("");
            return;
        }
        IntStream.iterate(start, i -> i <= end && end - i + 1 >= r - index, i -> i + 1).forEach(i -> {
            data[index] = questions[i];
            combinationUtil(questions, data, i + 1, end, index + 1, r);
        });

    }

    static void printCombination(String[] questions, int n, int r) {
        String[] data = new String[r];
        combinationUtil(questions, data, 0, n - 1, 0, r);
        for (String s : listString) {
            for (int j = 0; j < questions.length; j++) {
                if (s.equals(questions[j])) {
                    listInt.add(j);
                }
            }
        }
    }

    //Էկսպերտի գնահատականները
    public void questToExpert() {
        for (int i = 0; i < N; i++) {
            System.out.println("Expert N" + (i + 1));
            for (int j = 0; j < listInt.size(); j += 2) {
                System.out.print(listString.get(j) + " ");
                System.out.println(listString.get(j + 1));
                matrix[j][i] = input.nextInt();
                switch (matrix[j][i]) {
                    case 1 -> {
                        matrix[j + 1][i] = 0;
                        System.out.println("\t" + 0);
                    }
                    case 0 -> {
                        matrix[j + 1][i] = 1;
                        System.out.println("\t" + 1);
                    }
                }
            }
        }
    }

    //Խմբակային գնահատական + Կարգերի միջինացված արժեք
    public void allRates() {
        for (int i = 0; i < listInt.size(); i++) {
            int rates = 0;
            for (int j = 0; j < N; j++) {
                rates += matrix[i][j];
            }
            groupRates[listInt.get(i)] += rates;
        }
        IntStream.range(0, K).forEach(i -> {
            System.out.println(i + 1 + "-ին օբյեկտի խմբակային գնահատականը՝ " + groupRates[i]);
            midValue += groupRates[i];
        });
        midValue /= K;
        System.out.println("\nԿարգերի միջինացված արժեքը` " + midValue + "\n");
    }

    //Կարգերի շեղում + շեղումների քառակուսիների գումար
    public void differenceToMiddle() {
        IntStream.range(0, K).forEach(i -> {
            differenceToMidValue[i] = groupRates[i] - midValue;
            System.out.println(i + 1 + "-ին օբյեկտի համար յուրաքանչյուր կարգի շեղումը միջինից՝ " + differenceToMidValue[i]);
            S += Math.pow(differenceToMidValue[i], 2);
        });
        System.out.println("\nS (Շեղումների քառակուսիների գումար) = " + S);
    }

    //Կոնկորդացիայի գործակից
    public float concordanceConfident() {
        return (float) (12 * S / (Math.pow(N, 2) * (Math.pow(K, 3)) - K));
    }

    public static void main(String[] args) {

        System.out.print("K (դասակարգվող օբյեկտների թիվ) = ");
        int k = input.nextInt();
        System.out.print("N (փորձագետների թիվ) = ");
        int n = input.nextInt();
        int c = 2 * factorial(k) / (factorial(2) * factorial(k - 2));

        DelphiMethodEvenByEven evenByEven = new DelphiMethodEvenByEven(k, n, c);

        evenByEven.questions();
        System.out.println(listInt);
        evenByEven.questToExpert();

        System.out.println();
        evenByEven.allRates();
        evenByEven.differenceToMiddle();

        System.out.println("W (կոնկորդացիայի գործակից) = " + evenByEven.concordanceConfident());
    }

    static int factorial(int n) {
        if (n == 0)
            return 1;
        else
            return (n * factorial(n - 1));
    }
}