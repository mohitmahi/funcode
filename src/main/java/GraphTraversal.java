import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: Mohit
 */

// Graph with No Cycle => Tree
public class GraphTraversal {


    public static void main(String[] args) {
        final Node startNode = Node.builder().value("A").build();
        final Graph graph = getAGraph();

        visitBFS(graph, startNode);
        visitDFS(graph, startNode);

        System.out.println("\n\nUnit-Tests");
        new UnitTest().executeAllTestCases(); //Run-Test-cases
    }

    private static void visitBFS(Graph graph, Node startNode) {
        if (isInValidInput(graph, startNode)) return;

        final Queue<Node> queue = new ArrayDeque<>();
        queue.add(startNode);

        System.out.print("BFS ::");
        while (!queue.isEmpty()) {
            final Node poll = queue.poll();
            System.out.print(poll.getValue() + "=>");
            queue.addAll(graph.getAdjList().getOrDefault(poll, new ArrayList<>()));
        }
    }

    private static void visitDFS(Graph graph, Node startNode) {
        if (isInValidInput(graph, startNode)) return;

        final Stack<Node> stack = new Stack<>();
        stack.push(startNode);

        System.out.print("\nDFS ::");
        while (!stack.isEmpty()) {
            final Node poll = stack.pop();
            System.out.print(poll.getValue() + "=>");
            graph.getAdjList().getOrDefault(poll, new ArrayList<>()).forEach(stack::push);
        }
    }

    private static boolean isInValidInput(Graph graph, Node startNode) {
        return graph.isEmpty()
                || startNode == null
                || startNode.getValue() == null
                || startNode.getValue().isBlank()
                || !graph.getAdjList().containsKey(startNode);
    }

    private static Graph getAGraph() {
        //       A      X->Y
        //      / \
        //     B   C
        //    /\   /\
        //   D E  F G
        //  /\
        // H  I
        return Graph.builder()
                .adjList(Map.of(
                        Node.builder().value("A").build(),
                        List.of(Node.builder().value("B").build(), Node.builder().value("C").build()),

                        Node.builder().value("B").build(),
                        List.of(Node.builder().value("D").build(), Node.builder().value("E").build()),

                        Node.builder().value("C").build(),
                        List.of(Node.builder().value("F").build(), Node.builder().value("G").build()),

                        Node.builder().value("D").build(),
                        List.of(Node.builder().value("H").build(), Node.builder().value("I").build())
                ))
                .build();
    }

    @Builder
    @Data
    static class Graph {
        Map<Node, List<Node>> adjList;

        boolean isEmpty() {
            return adjList == null || adjList.isEmpty();
        }
    }
    @Builder
    @Data
    static class Node {
        String value;
    }

    static class UnitTest {
        @Test
        public void executeAllTestCases() {
            final Node startNode = Node.builder().value("A").build();
            final Graph graph = getAGraph();

            System.out.println("Test::1 (HappyCase)");
            visitBFS(graph, startNode);
            visitDFS(graph, startNode);

            System.out.println("\nTest::2 (Empty)");
            visitDFS(graph, null);
            visitBFS(graph, null);

            System.out.println("\nTest::3 (Empty)");
            visitDFS(graph, Node.builder().value("Z").build());
            visitBFS(graph, Node.builder().value("Z").build());

            System.out.println("\nTest::4 (Empty)");
            visitDFS(graph, Node.builder().build());
            visitBFS(graph, Node.builder().build());

            System.out.println("\nTest::5 (Empty)");
            visitDFS(Graph.builder().build(), Node.builder().build());
            visitBFS(Graph.builder().build(), Node.builder().build());
        }
    }
}
