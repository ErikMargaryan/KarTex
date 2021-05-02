package com.algorithms.delphi;

import java.util.*;

public class DelphiMethodEvenByEven {

    public int K;
    public int N;
    public int C;
    public int[][] matrix;
    public int[][] test;
    public String[] questions;
    public float midValue = 0;
    static List<String> listString;
    static List<Integer> listInt;
    static List<Integer> test1;
    int[] groupRates;
    public float[] differenceToMidValue;
    float S = 0;


    static Scanner input = new Scanner(System.in);

    public DelphiMethodEvenByEven(int K, int N, int C) {
        this.K = K;
        this.N = N;
        this.C = C;
        this.matrix = new int[C][C];
        this.test = new int[K][C];//kc
        this.questions = new String[K];
        listString = new ArrayList<>();
        listInt = new ArrayList<>();
        test1 = new ArrayList<>();
        groupRates = new int[K];
        this.differenceToMidValue = new float[K];
    }

    //Դիտարկվող օբյեկտներ
    public void questions() {
        questions[0] = "1) Դասախոսի գիտելիքները․ ";
        questions[1] = "2) Աշխատանքային հեռանկար․ ";
        questions[2] = "3) Լսարանային պայմանները․ ";
//        questions[3] = "4) Մատուցման ձևը․ ";
        int r = 2;
        int n = questions.length;
        printCombination(questions, n, r);
        System.out.println(Arrays.toString(questions));
    }

    static void combinationUtil(String[] questions, String[] data, int start,
                                int end, int index, int r) {

        if (index == r) {
            for (int j = 0; j < r; j++) {
                System.out.print(data[j] + " ");
                listString.add(data[j]);
//                listInt.add(j);
            }
            System.out.println("");
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = questions[i];
            combinationUtil(questions, data, i + 1, end, index + 1, r);
        }

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


    public void delphiMatrix() {
        for (int i = 0; i < C; i++) {
            for (int j = 0; j < N; j++) { //N
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
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
                if (matrix[j][i] == 1) {
                    matrix[j + 1][i] = 0;
                    System.out.println("\t" + 0);
                } else if (matrix[j][i] == 0) {
                    matrix[j + 1][i] = 1;
                    System.out.println("\t" + 1);
                }
            }
        }
        for (int i = 0; i < N; i++) {//N
            for (int j = 0; j < listInt.size(); j++) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();

        delphiMatrix();
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
        for (int i = 0; i < K; i++) {
            System.out.println(i + 1 + "-ին օբյեկտի խմբակային գնահատականը՝ " + groupRates[i]);
            midValue += groupRates[i];
        }
        midValue /= K;
        System.out.println("\nԿարգերի միջինացված արժեքը` " + midValue);
    }

    //Կարգերի շեղում + շեղումների քառակուսիների գումար
    public void differenceToMiddle() {
        for (int i = 0; i < K; i++) {
            differenceToMidValue[i] = groupRates[i] - midValue;
            System.out.println(i + 1 + "-ին օբյեկտի համար յուրաքանչյուր կարգի շեղումը միջինից՝ " + differenceToMidValue[i]);
            S += Math.pow(differenceToMidValue[i], 2);
        }
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
//        evenByEven.testMatrix();
        evenByEven.allRates();
        System.out.println(Arrays.toString(evenByEven.groupRates));
        evenByEven.differenceToMiddle();
        evenByEven.concordanceConfident();

    }

    static int factorial(int n) {
        if (n == 0)
            return 1;
        else
            return (n * factorial(n - 1));
    }
}
