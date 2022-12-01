// https://nus.kattis.com/problems/brevoptimering

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Deque;

/*
Idea 1: Dynamic Programming: Let U[k] be the output of person k,
then U[k] = Math.min(sum of U[n] for n giving letters to k, M[k])
Idea 2: Topological ordering: Send letters by the topological ordering. (this seems to be faster)
 */

public class LetterOptimization {
    private static void dfs(int[][][] edge, boolean[] visited, Deque<Integer> order, int person) {
        // Complete dfs
        if (visited[person]) return;
        visited[person] = true;
        for (int[] other : edge[person]) {
            dfs(edge, visited, order, other[0]);
        }
        // Push onto stack.
        order.push(person);
    }

    private static void optimizeLetter(int[][][] edge, double[] efficiency) {
        // Do a topological ordering of the vertexes.
        Deque<Integer> order = new LinkedList<>();
        boolean[] visited = new boolean[efficiency.length];
        for (int i = 0; i < efficiency.length; i++) {
            dfs(edge, visited, order, i);
        }
        double[] dp = new double[efficiency.length];
        boolean[] optimized = new boolean[efficiency.length];
        for (int ordered : order) {
            // The start of the letter production
            if (dp[ordered] == 0 || dp[ordered] - efficiency[ordered] > 0.0001) {
                optimized[ordered] = true;
                dp[ordered] = efficiency[ordered];
            }
            for (int[] other : edge[ordered]) {
                double temp = dp[other[0]] + dp[ordered] * other[1] * 0.01;
                dp[other[0]] = temp;
            }
        }
        for (int i = 0; i < efficiency.length; i++) {
            if (optimized[i]) System.out.println(i + 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int people = sc.nextInt();
        double[] efficiency = new double[people];
        int[][][] edge = new int[people][][];

        for (int i = 0; i < people; i++) {
            efficiency[i] = sc.nextInt();
            edge[i] = new int[sc.nextInt()][2];
            for (int j = 0; j < edge[i].length; j++) {
                edge[i][j][0] = sc.nextInt() - 1;
                edge[i][j][1] = sc.nextInt();
            }
        }
        optimizeLetter(edge, efficiency);
    }
}

