package com.algorithms.delphi;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Delphi's method
 *
 * @author Erik Margaryan
 * @version 1.0
 * @since 25.04.2021
 */

public class DelphiMethod {

    static Scanner input = new Scanner(System.in);

    public int K;
    public int N;
    public int[][] matrix;
    public String[] questions;
    public float midValue = 0;
    int[] groupRates;
    public float[] differenceToMidValue;
    float S = 0;

    public DelphiMethod(int K, int N) {
        this.K = K;
        this.N = N;
        matrix = new int[K][N];
        questions = new String[K];
        groupRates = new int[K];
        differenceToMidValue = new float[K];
    }

    //Դիտարկվող օբյեկտներ
    public void questions() {
        questions[0] = "1) Դասախոսի գիտելիքները․ ";
        questions[1] = "2) Աշխատանքային հեռանկար․ ";
        questions[2] = "3) Լսարանային պայմանները․ ";
//        questions[3] = "4) Մատուցման ձևը․ ";
        System.out.println(Arrays.toString(questions));
    }

    //Դելֆիի մատրից
    public void delphiMatrix() {
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    //Էկսպերտի գնահատականները
    public void questToExpert() {
        for (int i = 0; i < N; i++) {
            System.out.println("Expert N" + (i + 1));
            for (int j = 0; j < K; j++) {
                System.out.print(questions[j] + " ");
                matrix[j][i] = input.nextInt();
            }
        }
        delphiMatrix();
    }

    //Խմբակային գնահատական + Կարգերի միջինացված արժեք
    public void allRates() {
        for (int i = 0; i < K; i++) {
            int rates = 0;
            for (int j = 0; j < N; j++) {
                rates += matrix[i][j];
            }
            groupRates[i] = rates;
            System.out.println((i + 1) + "-ին օբյեկտի խմբակային գնահատականը՝ " + rates);
            midValue += rates;
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

//        Scanner input = new Scanner(System.in);

        System.out.print("K (դասակարգվող օբյեկտների թիվ) = ");
        int k = input.nextInt();
        System.out.print("N (փորձագետների թիվ) = ");
        int n = input.nextInt();
        DelphiMethod delphi = new DelphiMethod(k, n);

        delphi.questions();
        delphi.questToExpert();

        System.out.println();
        delphi.allRates();

        System.out.println();
        delphi.differenceToMiddle();

        System.out.println("W (կոնկորդացիայի գործակից) = " + delphi.concordanceConfident());
    }
}
