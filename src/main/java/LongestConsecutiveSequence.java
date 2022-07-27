import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {

    // Brute-Force: Sort and find in N + NLogN
    // O(N) Find a number with no left part (-1) and start seq from that one,
    static int findLongestConSeq(int[] input) {
        final HashSet<Integer> set = new HashSet<>();
        Arrays.stream(input).forEach(set::add);
        int longest = 0;
        for(int i: input) {
            int len=0;
            if(set.contains(i -1)) {
                while(set.contains(i+len)) len++;

                longest = Math.max(longest, len);
            }
        }
        return longest;
    }

    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int max = -1;
        int min = 1000000000;

        // Find min and max from array & add each to set
        for (int i : nums) {
            set.add(i);
            max = Math.max(max, i);
            min = Math.min(min, i);
        }

        int c = 0; // count streak
        int res = 0; // longest streak
        for (int i = min; i <= max; ++i) {
            // Check if set contains ith value; increment count & remove from set
            if (set.contains(i)) {
                c++;
                set.remove(i);
            } else {
                // If not found set count to 0
                c = 0;
            }
            // Find longest streak at every step in case we break out from loop
            res = Math.max(res, c);

            // If set size is less than current longest streak break as we wont get any longer streak
            if (set.size() <= res && c == 0) break;
        }
        return res;
    }
}
