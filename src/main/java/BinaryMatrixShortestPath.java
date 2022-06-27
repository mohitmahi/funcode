import lombok.Builder;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

//Travel from left-top-most to right-down-most, just allowed to traverse "0", find shorted distance.
public class BinaryMatrixShortestPath {

    public static void main(String[] args) {
        //    0  0  1
        //    0  1  0
        //    0  0  0
        int[][] grid = {{0,0,1}, {0,1,0}, {0,0,0}};
        System.out.println(findShortestPath(grid)); // ==> 4
    }

    //BFS (O(N)
    static int findShortestPath(int[][] grid) {
        if(grid[0][0] != 0) return -1;

        int MaxRow = grid.length;
        int MaxCol = grid[0].length;

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(Node.builder().row(0).col(0).distance(1).build());
        grid[0][0] = 1;

        List<Point> directions = List.of(
                Point.builder().x(0).y(1).build(),
                Point.builder().x(0).y(-1).build(),
                Point.builder().x(1).y(0).build(),
                Point.builder().x(1).y(1).build(),
                Point.builder().x(1).y(-1).build(),
                Point.builder().x(-1).y(0).build(),
                Point.builder().x(-1).y(1).build(),
                Point.builder().x(-1).y(-1).build()
        );

        while (!queue.isEmpty()) {

            final Node current = queue.poll();
            System.out.println(current);

            if(current.row == MaxRow-1 && current.col == MaxCol-1) return current.distance;

            for(Point direction: directions) {
                int row = current.row + direction.x;
                int col = current.col + direction.y;

                if(row >= 0 && col >=0 && row < MaxRow && col < MaxCol ) {
                    if (grid[row][col] == 0) {
                        queue.add(Node.builder().row(row).col(col).distance(current.distance + 1).build());
                        grid[row][col] = 1;
                    }
                }
            }
        }
        return -1;
    }

    @Data
    @Builder
    static class Node {
        int row;
        int col;
        int distance;
    }

    @Data
    @Builder
    static class Point {
        int x;
        int y;
    }
}
