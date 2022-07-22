import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//https://leetcode.com/problems/longest-string-chain/discuss/2153004/Explaining-Three-Approaches-or-JAVA
public class LongestStringChain {
    private static Set<String> wordDict;
    private static Map<String, Integer> memo;

    public static void main(String[] args) {
        String[] input = {"a","b","ba","bca","bda","bdca","bdcaf","bdcaee","bdcae","bdcafwefe","bdcafefwe"};
        System.out.println(longestStrChain(input));
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
