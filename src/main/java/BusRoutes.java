import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BusRoutes {
 // https://www.youtube.com/watch?v=R58Q0J52qzI&ab_channel=code_report

    public static void main(String[] args) {
        int[][] routes = {{1,2,7}, {3,6,7}};
        final int minBuses = numBusesToDestination(routes, 1, 6);
        System.out.println(minBuses);
    }

    public static int numBusesToDestination(int[][] routes, int start, int targetStop) {
        int n = routes.length;
        HashMap<Integer, HashSet<Integer>> to_routes = new HashMap<>();
        for (int route = 0; route < routes.length; route++) {
            for (int stops : routes[route]) {
                to_routes.computeIfAbsent(stops, key -> new HashSet<>()).add(route);
            }
        }
        Queue<AbstractMap.SimpleEntry<Integer, Integer>> bfs = new LinkedList<>();
        AbstractMap.SimpleEntry<Integer, Integer> entry = new AbstractMap.SimpleEntry<>(start, 0); // Start with No Bus (0)
        bfs.offer(entry);

        HashSet<Integer> stopSeen = new HashSet<>();
        stopSeen.add(start);

        boolean[] seen_routes = new boolean[n];

        while (!bfs.isEmpty()) {
            int currentStop = bfs.peek().getKey(), bus = bfs.peek().getValue();
            bfs.poll();
            if (currentStop == targetStop) return bus;
            for (int route : to_routes.get(currentStop)) {
                if (seen_routes[route]) continue;
                for (int stops : routes[route]) {
                    if (!stopSeen.contains(stops)) {
                        stopSeen.add(stops);
                        bfs.offer(new AbstractMap.SimpleEntry<>(stops, bus + 1));
                    }
                }
                seen_routes[route] = true;
            }
        }
        return -1;
    }
}
