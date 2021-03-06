package Airbnb;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 787. Cheapest Flights Within K Stops
 * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
 *
 * Now given all the cities and flights, together with starting city src and the destination dst, your task is to find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.
 *
 * Example 1:
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 1
 * Output: 200
 * Explanation:
 * The graph looks like this:
 *
 *
 * The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
 * Example 2:
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 0
 * Output: 500
 * Explanation:
 * The graph looks like this:
 *
 *
 * The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
 * Note:
 *
 * The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
 * The size of flights will be in range [0, n * (n - 1) / 2].
 * The format of each flight will be (src, dst, price).
 * The price of each flight will be in the range [1, 10000].
 * k is in the range of [0, n - 1].
 * There will not be any duplicated flights or self cycles.
 */
public class CheapestFlightsWithKStops {
    public static int findCheapestPriceBFS(int n, int[][] flights, int src, int dst, int K) {

        // value of price[i][j] is the flight price from city i to city j, -1 if not exists
        int[][] prices = new int[n][n];
        for (int[] arr : prices) {
            Arrays.fill(arr, -1);
        }

        // set prices
        for (int[] flight : flights) {
            // prices[from][to] = price
            prices[flight[0]][flight[1]] = flight[2];
        }

        // Dijkstra
        // city, price, stop
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] - b[1])); // sort by price
        pq.offer(new int[]{src, 0, 0});

        while (!pq.isEmpty()) {
            int[] curCheapest = pq.poll();
            int city = curCheapest[0];
            int price = curCheapest[1];
            int stop = curCheapest[2];
            if (stop > K + 1) { // why K + 1?
                continue;
            }
            if (city == dst) {
                return price;
            }
            int[] flightPricesFromCurCity = prices[city];
            for (int i = 0; i < flightPricesFromCurCity.length; i++) {
                int p = flightPricesFromCurCity[i];
                if (p == -1) {
                    continue;
                }
                pq.offer(new int[]{i, price + p, stop + 1});
            }
        }
        return -1;
    }

    public static int findCheapestPriceDFS(int n, int[][] flights, int src, int dst, int K) {

        // value of price[i][j] is the flight price from city i to city j, -1 if not exists
        int[][] prices = new int[n][n];
        for (int[] arr : prices) {
            Arrays.fill(arr, -1);
        }

        // set prices
        for (int[] flight : flights) {
            // prices[from][to] = price
            prices[flight[0]][flight[1]] = flight[2];
        }

        boolean[] visited = new boolean[n];
        int[] result = new int[]{Integer.MAX_VALUE};

        dfs(src, dst, K + 1 /* number of flights one can take */, prices, visited, 0, result);

        return result[0] == Integer.MAX_VALUE ? -1 : result[0];
    }

    private static void dfs(int src, int dst, int K, int[][] prices, boolean[] visited, int cost, int[] result) {
        if (src == dst) {
            result[0] = cost;
            return;
        }
        if (K == 0) {
            return;
        }
        visited[src] = true;
        for (int i = 0; i < prices[src].length; i++) {
            int nextFlightCost = prices[src][i];
            if (nextFlightCost != -1 && !visited[i]) {
                if (cost + nextFlightCost >= result[0]) {
                    continue;
                }
                dfs(i, dst, K - 1, prices, visited, cost + nextFlightCost, result);
            }
        }
        visited[src] = false;
    }

    public static void main(String[] args) {
        System.out.println(findCheapestPriceBFS(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0, 2, 1));
        System.out.println(findCheapestPriceDFS(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0, 2, 1));
    }
}
