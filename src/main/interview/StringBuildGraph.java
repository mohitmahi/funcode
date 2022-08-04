public class StringBuildGraph {
    /*
    Given a grid of characters, you may start at any letter and move in any of the 4 directions (up, down, right, left) to an unvisited character, appending as you go to build a string. Write a function that returns whether a given string appears in the grid.

    Example Grids:

    // Python/JS Representation
    [
     ['O', 'A', 'A', 'N'],
     ['A', 'T', 'F', 'E'],
     ['I', 'H', 'K', 'R'],
     ['I', 'F', 'L', 'V'],
    ]
    // Java Representation
    {
     {'O', 'A', 'A', 'N'},
     {'A', 'T', 'F', 'E'},
     {'I', 'H', 'K', 'R'},
     {'I', 'F', 'L', 'V'},
    }

    // EWNS
    // TFE
    // OATAA - Visited needs to reset
    // "A" right or "A" bottom
    // since both matches the char[0] of

    // Duplicates: can be on diff position 'cell'

    // 'THFA' - Fail Fast if first chart is matching with any of my sibling then only fdo dfs

    */
    static class Solution {

        static boolean isStringAllowed(String toMatch, char[][] input) {
            int maxRow = input.length;
            int maxCol = input[0].length;

            // TODO if string is empty return false

            for (int cRow = 0; cRow < maxRow; cRow++) {
                for (int cCol = 0; cCol < maxCol; cCol++) {
                    char current = input[cRow][cCol];

                    boolean canBeBuild = false;
                    if (current == toMatch.charAt(0)) {
                        boolean[][] visited = new boolean[maxRow][maxCol];
                        visited[cRow][cCol] = true;
                        System.out.println("Added: " + input[cRow][cCol]);
                        System.out.println("Remaining: " + toMatch.substring(1));
                        canBeBuild = doDfs(toMatch.substring(1), input, cCol, cRow, visited);
                    }
                    if (canBeBuild) return true;
                }
            }
            return false;
        }

        static boolean doDfs(String toMatch, char[][] input, int sRow, int sCol, boolean[][] visited) {

            System.out.println("LastVisited: row " + sRow + ", col " + sCol + " toMatch: " + toMatch);

            if (toMatch.length() == 0) return true;

            int[][] directions = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

            for (int[] currentDirection : directions) {
                int nextRow = sRow + currentDirection[0];
                int nextCol = sCol + currentDirection[1];

                if (nextRow < 0 || nextRow > input.length || nextCol < 0 || nextCol > input[0].length) {
                    System.out.println("Skip-LastVisited: row " + nextRow + ", col " + nextCol + " toMatch: " + toMatch);
                    System.out.println("Skip OOB:" + toMatch);
                    continue;
                }

                if (visited[nextRow][nextCol]) {
                    System.out.println("Already Visited: row" + nextRow + ", col" + nextCol + ", " + toMatch);
                    continue;
                }

                if (input[nextRow][nextCol] == toMatch.charAt(0)) {
                    if (toMatch.length() == 1) return true;
                    System.out.println("Added: " + input[nextRow][nextCol]);
                    System.out.println("Remaining: " + toMatch.substring(1));
                   // visited = new boolean[input.length][input[0].length];
                    visited[nextRow][nextCol] = true;
                    boolean canBeBuild = doDfs(toMatch.substring(1), input, nextRow, nextCol, visited);
                    if (canBeBuild) return true;
                }
            }
            return false;
        }

        public static void main(String[] args) {
        /*
        {
            {'O', 'A', 'A', 'N'},
            {'A', 'T', 'F', 'E'},
            {'I', 'H', 'K', 'R'},
            {'I', 'F', 'L', 'V'},
            }
        */
            char[][] input = new char[][]{{'O', 'A', 'A', 'N'}, {'A', 'T', 'F', 'E'}, {'I', 'H', 'K', 'R'}, {'I', 'F', 'L', 'V'}};
            //System.out.println(isStringAllowed("OAT",input)); // True
             //System.out.println(isStringAllowed("TFE",input)); // True
             //System.out.println(isStringAllowed("OATHA",input)); //False
            System.out.println(isStringAllowed("OATAA", input)); //True
        }
    }
}