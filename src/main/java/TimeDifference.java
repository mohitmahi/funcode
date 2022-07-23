import java.util.Arrays;
import java.util.List;

public class TimeDifference {

    //Given a list of 24-hour clock time points in "HH:MM" format, return the minimum minutes difference between any two time-points in the list.


    //We need to transform time to ints first, for that iterate over the array and calcualte every one as hours * 60 + mins. Then we need to sort times because the min time will be between two next times in a sorted array, every other times will be of greater distances.
    //Catch is with the last (greatest) time - it can be closer to the smallest one, so for this pair we'll do a special check.
    public int findMinDifference(List<String> timePoints) {
        int res = Integer.MAX_VALUE;
        int N = timePoints.size();
        int[] c = new int[N];

        for (int i = 0; i < N; i++) {
            String s = timePoints.get(i);
            c[i] = Integer.parseInt(s.substring(0, 2)) * 60 + Integer.parseInt(s.substring(3, 5));
        }
        Arrays.sort(c);
        for (int i = 1; i < N; i++) {
            res = Math.min(res, c[i] - c[i - 1]);
        }
        res = Math.min(res, c[0] + (24*60 - c[N - 1]));
        return res;
    }
}
