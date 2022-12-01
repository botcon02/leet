// https://open.kattis.com/problems/reachableroads

import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;

public class ReachableRoads {
    private static void dfs(int curr, HashMap<Integer, HashSet<Integer>> map, HashSet<Integer> visited) {
        visited.add(curr);
        HashSet<Integer> adjacency = map.get(curr);
        for (int endpoint : adjacency) {
            if (!visited.contains(endpoint)) {
                dfs(endpoint, map, visited);
            }
        }
    }

    private static int minRoadAdded(int[][] pairs, int endpoints) {
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < endpoints; i++) {
            map.put(i, new HashSet<>());
        }
        for (int[] pair : pairs) {
            map.get(pair[0]).add(pair[1]);
            map.get(pair[1]).add(pair[0]);
        }
        HashSet<Integer> visited = new HashSet<>();
        int connectedComponents = 0;
        for (int i = 0; i < endpoints; i++) {
            if (!visited.contains(i)) {
                connectedComponents++;
                dfs(i, map, visited);
            }
        }
        return connectedComponents - 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cities = sc.nextInt();
        for (int i = 0; i < cities; i++) {
            int endpoints = sc.nextInt();
            int[][] pairs = new int[sc.nextInt()][2];
            for (int j = 0; j < pairs.length; j++) {
                pairs[j][0] = sc.nextInt();
                pairs[j][1] = sc.nextInt();
            }
            System.out.println(minRoadAdded(pairs, endpoints));
        }
    }
}
