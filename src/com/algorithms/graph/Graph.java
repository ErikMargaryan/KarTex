package com.algorithms.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Graph-ի ալգորիթմ կառ․ տեխ․-ի համար
 *
 * @version 5.6
 * @autor Erik Margaryan
 * @since 05.03.2021
 */

public class Graph {
    public int vertices;
    public int[][] adjacencyMatrix;
    public int[][] result;
    public int[][] checkedMatrix;
    public int[][] sum;
    public ArrayList<Integer> fromList;
    public ArrayList<Integer> toList;
    public ArrayList<Integer> T1;
    public ArrayList<Integer> T2;
    public ArrayList<Integer> T3;
    public int N;
    double kmoAll;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyMatrix = new int[vertices][vertices];
        result = new int[vertices][vertices];
        checkedMatrix = new int[vertices][vertices];
        sum = new int[vertices][vertices];
        T1 = new ArrayList<>();
        T2 = new ArrayList<>();
        T3 = new ArrayList<>();
        fromList = new ArrayList<>();
        toList = new ArrayList<>();
        N = 2;
    }

    //For add edge ant t3
    public void addEdge(int from, int to) {
        //for out elements (t3)
        if (to == 0) {
            T3.add(from);
            return;
        }
        fromList.add(from);
        toList.add(to);
        from--;
        to--;
        for (int i = 0; i < vertices; i++)
            for (int j = 0; j < vertices; j++)
                adjacencyMatrix[from][to] = 1;
    }

    //for (A)
    public void printAdjacencyMatrix() {
        System.out.println("-----------------A-----------------");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++)
                System.out.print(adjacencyMatrix[i][j] + " ");
            System.out.println();
        }
    }

    //For t1, t2
    public void elementsAboutT12() {
        Set<Integer> T1c;
        Set<Integer> T2c;
        for (Integer integer : fromList) {
            boolean b = false;
            for (Integer value : toList) {
                if (value.equals(integer)) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                T1.add(integer);
                T1c = new HashSet<>(T1);
                T1.clear();
                T1.addAll(T1c);
            } else {
                T2.add(integer);
                T2c = new HashSet<>(T2);
                T2.clear();
                T2.addAll(T2c);
            }
        }
    }

    //For t4
    public void printT4forA1() {
        detectsTt4(adjacencyMatrix);
    }

    public void printT4forA1toAN(int[][] current) {
        detectsTt4(current);
    }

    private void detectsTt4(int[][] current) {
        int t4 = 0;
        for (int i = 0; i < vertices; i++) {
            boolean b = false;
            for (int j = 0; j < vertices; j++) {
                if (current[j][i] != 0) {
                    b = true;
                    break;
                }
            }
            if (!b)
                t4++;
        }
        System.out.println("N = " + (N - 1));
        System.out.println("t4(ֆորմալ անջատված տարրերի քանակ) = " + t4);
        int t7;
        t7 = t4 - N + 1;
        System.out.println("t7(ֆորմալ անջատված տարրերի միջոցցով ձևավորված տարրերի թիվը) = " + t7);
        float kmo = (float) t7 / t4;
        System.out.println("KՄO = " + kmo);
        kmoAll += kmo;
    }

    //For multiplication
    public void multipleMatrix(int[][] matrix1, int[][] matrix2, int[][] result) {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                result[i][j] = 0;
                for (int k = 0; k < vertices; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        for (int i = 0; i < vertices; i++)
            System.arraycopy(result[i], 0, checkedMatrix[i], 0, vertices);
    }

    //For Elements checking [0, 1]
    public boolean checkElements(int[][] result) {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (result[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    //For S
    public void sumMatrices() {
        System.out.println("\n-----------------S-----------------");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                sum[i][j] += adjacencyMatrix[i][j];
                System.out.print(sum[i][j] + " ");
            }
            System.out.println();
        }
    }

    //For t5, t6, kn
    public void detectT5T6() {
        //for t5
        int t5 = 0;
        float Kn;
        for (int i = 0; i < T2.size(); i++) {
            for (int j = 0; j < fromList.size(); j++) {
                if (T2.get(i).equals(fromList.get(j))) {
                    for (Integer integer : T2) {
                        if (toList.get(j).equals(integer)) {
                            t5++;
                        }
                    }
                }
            }
        }
        t5 -= T3.size();
        System.out.println("t5(միջանկյալ տարրերի միջև կապերի քանակ) = " + t5);
        Kn = (float) t5 / fromList.size();
        System.out.println("Kn(ներքին կապերի գործակից) = " + Kn);
        //for t6
        int t6 = 0;
        float Kk;
        for (int i = 0; i < T3.size(); i++) {
            for (int j = 0; j < fromList.size(); j++) {
                if (T3.get(i).equals(fromList.get(j))) {
                    for (Integer integer : T3) {
                        if (toList.get(j).equals(integer)) {
                            t6++;
                        }
                    }
                }
            }
        }
        System.out.println("t6(ելքային տարրերի միջև կապերի քանակ) = " + t6);
        Kk = (float) (2 * t6) / (T3.size() * (T3.size() - 1));
        System.out.println("Kk(կրկնման գործակից) = " + Kk);
    }

    //For Km
    public float detectKm(float t2, float m) {
        return t2 / m;
    }

    //For detecting Cycle
    public void isCycle(int[][] matrix) throws Exception {
        for (int i = 0; i < vertices; i++) {
            if (matrix[i][i] != 0) {
                throw new Exception("Graph-ում կա ցիկլ");
            }
        }
    }


    public static void main(String[] args) throws Exception {

        Graph graph = new Graph(18);

        //Adding Edges
        graph.addEdge(1, 5);
        graph.addEdge(2, 6);
        graph.addEdge(3, 4);
        graph.addEdge(4, 9);
        graph.addEdge(5, 7);
        graph.addEdge(6, 8);
        graph.addEdge(7, 11);
//        graph.addEdge(7, 1);
        graph.addEdge(8, 12);
        graph.addEdge(9, 10);
        graph.addEdge(10, 15);
        graph.addEdge(11, 13);
        graph.addEdge(12, 14);
        graph.addEdge(13, 17);
        graph.addEdge(14, 18);
        graph.addEdge(15, 16);
        graph.addEdge(16, 17);
        graph.addEdge(17, 18);
        graph.addEdge(16, 0);
        graph.addEdge(17, 0);
        graph.addEdge(18, 0);

        //For t1, t2
        graph.elementsAboutT12();

        //For in and out Elements
        System.out.println("Ci = " + graph.fromList);
        System.out.println("Cj = " + graph.toList);

        //For cycle checking
        graph.isCycle(graph.adjacencyMatrix);

        //for (A)
        graph.printAdjacencyMatrix();

        graph.printT4forA1();

        //For multiple
        for (int i = 0; i < graph.vertices; i++) {
            System.arraycopy(graph.adjacencyMatrix[i], 0, graph.checkedMatrix[i], 0, graph.vertices);
        }

        //For repeated multiplication
        do {
            System.out.println("\n----------------A" + graph.N + "-----------------");
            perfectMultipleHalf(graph);
            graph.N++;
            //check for cycle
            graph.isCycle(graph.result);
            graph.printT4forA1toAN(graph.result);
        } while (graph.checkElements(graph.result));
        System.out.println("\nN = " + (graph.N - 1));

        //For S
        graph.sumMatrices();

        System.out.println("\nՄուտքի տարրեր։ " + graph.T1 + ", t1 = " + graph.T1.size());
        System.out.println("Միջանկյալ տարրեր։ " + graph.T2 + ", t2 = " + graph.T2.size());
        System.out.println("Ելքի տարրեր։ " + graph.T3 + ", t3 = " + graph.T3.size());
        System.out.println("KՄO միջին  = " + graph.kmoAll / graph.N);

        //For t5, t6
        graph.detectT5T6();

        //For Km
        float Km = graph.detectKm(graph.T2.size(), graph.vertices);
        System.out.println("Km(միջանկյալ տարրերը ընդհանուր տարրերի մասը) = " + Km);

    }

    //For multiple matrices (half)
    public static void perfectMultipleHalf(Graph graph) {
        graph.multipleMatrix(graph.adjacencyMatrix, graph.checkedMatrix, graph.result);
        for (int i = 0; i < graph.vertices; i++) {
            for (int j = 0; j < graph.vertices; j++) {
                System.out.print(graph.result[i][j] + " ");
                graph.sum[i][j] += graph.result[i][j];
            }
            System.out.println();
        }
    }

}