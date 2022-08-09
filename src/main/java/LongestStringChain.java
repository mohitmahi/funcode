import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//https://leetcode.com/problems/longest-string-chain/discuss/2153004/Explaining-Three-Approaches-or-JAVA
// If its sorted, then simply its LIS (Longest Increasing Subsequence) kind of problem

public class LongestStringChain {
    private static Set<String> wordDict;
    private static Map<String, Integer> memo;

    public static void main(String[] args) {
        String[] input = {"a","b","ba","bca","bda","bdca","bdcaf","bdcaee","bdcae","bdcafwefe","bdcafefwe"};
        System.out.println(longestStrChain(input));
        System.out.println(longestStrChain_DP(input));
        System.out.println(longestStrChain_LIS(input));
    }

    public static int longestStrChain_DP(String[] words) {
        //LIS ==> Instead of arr[i] > arr[j] we need to check if arr[i].length > arr[j].length
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, Comparator.comparingInt(String::length));  //log N
        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }

    public static int longestStrChain_LIS(String[] words) {
        //LIS ==> Instead of arr[i] > arr[j] we need to check if arr[i].length > arr[j].length
        int[] dp = new int[words.length];
        Arrays.fill(dp, 1);
        Arrays.sort(words, Comparator.comparingInt(String::length)); //log N
        int res = 0;
        for (int i=1; i < words.length; i++) {
            for (int j=0; j < i; j++) {
                if (compare(words[i], words[j]) && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    private static boolean compare(String larger, String smaller) {
        if (smaller.length() + 1 != larger.length()) return false;
        int first = 0;
        int second = 0;
        while(first < larger.length() && second < smaller.length()) {
            if(larger.charAt(first) == smaller.charAt(second)) {
                first++;
                second++;
            } else {
                first++; //If first character is mismatch
            }
        }
        return second == smaller.length();
    }

    public static int longestStrChain(String[] words) {
        Long start = System.currentTimeMillis();
        wordDict = new HashSet<>();
        Collections.addAll(wordDict, words); // adding all words to a set for constant look-up // O(N)
        int maxPath = 1;
        memo = new HashMap<>();
        for (String word: words)
            maxPath = Math.max(maxPath, dfs(word)); // O(N) N => number of words

        Long end = System.currentTimeMillis() - start;
        System.out.println("Time::" + end);
        return maxPath;
    }

    private static int dfs(String word) {
        if (memo.containsKey(word)) return memo.get(word); // if we're computed this word before, return the result.

        StringBuilder sb = new StringBuilder(word); // not thread safe, so faster than String-Builder
        int maxPath = 0;
        // delete each character, check if that's a valid word in the set, add the character back and continue
        for (int i=0; i<word.length(); i++) {  // O(M) m => length of word[i]
            sb.deleteCharAt(i);               // O(M) m => length of word[i]
            String prevWord = sb.toString();
            if (wordDict.contains(prevWord))   // O(1) or O(LogN) (Using HashMap Containskey(), which uses HashCode to find Bucket than equals() in an AVL Tree since Java8)
                maxPath = Math.max(maxPath, dfs(prevWord));
            sb.insert(i, word.charAt(i));     // O(M) m => length of word[i]
        }

        memo.put(word, maxPath + 1); // store the result
        return memo.get(word);
    }
}
