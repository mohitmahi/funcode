// Longest Common Sub-Sequence between S1/S2

// Longest Pallindromic Sub-Sequence for S1 => Do S2 = rev(S1) and then find LCS between both

// Minimum inserton to make a string pallindrome  ==> Find Longest Pallindromic Sub-Sequence for s1, and then it will s2.lenght - pallindromSubSeq.lenght
//https://www.youtube.com/watch?v=xPBLEj41rFU&ab_channel=takeUforward
public class LCS {

    public int longestCommonSubsequence(String text1, String text2) {

        // Make a grid of 0's with text2.length() + 1 columns
        // and text1.length() + 1 rows.
        int[][] dpGrid = new int[text1.length() + 1][text2.length() + 1];

        // Iterate up each column, starting from the last one.
        for (int col = text2.length() - 1; col >= 0; col--) {
            for (int row = text1.length() - 1; row >= 0; row--) {
                // If the corresponding characters for this cell are the same...
                if (text1.charAt(row) == text2.charAt(col)) {
                    dpGrid[row][col] = 1 + dpGrid[row + 1][col + 1];
                    // Otherwise they must be different...
                } else {
                    dpGrid[row][col] = Math.max(dpGrid[row + 1][col], dpGrid[row][col + 1]);
                }
            }
        }

        // The original problem's answer is in dp_grid[0][0]. Return it.
        return dpGrid[0][0];
    }
}