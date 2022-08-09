import org.junit.jupiter.api.Assertions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BadAirport {

    static class Graph {
        int total_airport_code;
        LinkedList<Integer>[] adjList;

        Graph(int total_airport) {
            this.total_airport_code = total_airport;
            adjList = new LinkedList[total_airport_code];
            for(int i =0; i < total_airport_code; i++) {
                adjList[i] = new LinkedList<>();
            }
        }
        void connectAirport(int startingCode, int destinationCode) {
            this.adjList[startingCode].add(destinationCode);
        }
    }

    static boolean getAllPossiblePath(Graph graph, Integer source, Integer destination, List<List<Integer>> possiblePath, Set<Integer> visited) {
        final LinkedList<Integer> nextAirports = graph.adjList[source];
        visited.add(source);

        boolean allowed = false;
        for(Integer nextAirport: nextAirports) {
            if(Objects.equals(nextAirport, source) || visited.contains(nextAirport)) {
                continue;
            }
            if(Objects.equals(nextAirport, destination)) {
                List<Integer> possibleOnePath = new LinkedList<>(List.of(source, nextAirport));
                possiblePath.add(possibleOnePath);
                allowed = true;
            }
            else {
                if(getAllPossiblePath(graph, nextAirport, destination, possiblePath, visited)) {
                    for(List<Integer> possiblePathFound: possiblePath) {
                        if(possiblePathFound.get(0).equals(source)) continue;

                        possiblePathFound.add(0, source);
                        allowed = true;
                    }
                }
            }
        }
        return allowed;
    }

    public static void main(String[] args) {
        final Graph graph = new Graph(5); //0, 1 , 2,  3,  4
        graph.connectAirport(1, 2);  //1<->2
        graph.connectAirport(2, 1);

        graph.connectAirport(0, 1); //0<->1
        graph.connectAirport(1, 0);

        graph.connectAirport(0, 4); //0<->4
        graph.connectAirport(4, 0);

        graph.connectAirport(2, 4);  //2<->4
        graph.connectAirport(4, 2);

        //   [0] <---> [4] <--->  [2]
        //    /\                   /\
        //    |-----> [1] <--------|

        // [1, 0, 4] & [1, 2, 4] are only two possible.

        List<Integer> customerProvidePath = List.of(1,3,4);

        Integer source = customerProvidePath.get(0);
        Integer destination = customerProvidePath.get(customerProvidePath.size() - 1);

        final List<List<Integer>> possiblePaths = new LinkedList<>();

        final Set<Integer> visited = new HashSet<>();

        getAllPossiblePath(graph, source, destination, possiblePaths, visited); //If false possiblePaths will be empty

        System.out.println(possiblePaths); // [[1 , 2, 4], [1, 0, 4]]

        // *************** TEST ****************//
        validateResult(possiblePaths);

        //List<List<Integers>> optimalPaths = getTheMostKOptimalPath(possiblePath, kth); //production manager will define the rule to get score
    }

    private static void validateResult(List<List<Integer>> possiblePaths) {
        //[[1, 2, 4], [1, 0, 4]]
        Assertions.assertEquals(possiblePaths.size(), 2); // Total path

        //First Path ==> [1, 2, 4]
        Assertions.assertEquals(possiblePaths.get(0).size(), 3); // 3 Airports
        Assertions.assertEquals(possiblePaths.get(0).get(0), 1); //start
        Assertions.assertEquals(possiblePaths.get(0).get(1), 2); //start
        Assertions.assertEquals(possiblePaths.get(0).get(2), 4); //end

        //Second Path ==> [1, 0, 4]
        Assertions.assertEquals(possiblePaths.get(1).size(), 3); // 3 Airports
        Assertions.assertEquals(possiblePaths.get(1).get(0), 1); //start
        Assertions.assertEquals(possiblePaths.get(1).get(1), 0); //start
        Assertions.assertEquals(possiblePaths.get(1).get(2), 4); //end
    }

}
