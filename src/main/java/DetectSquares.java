import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://leetcode.com/problems/detect-squares/discuss/1472167/Java-Clean-solution-with-list-and-hashmap
//Similar idea, but we could avoid duplicate points int[] into the list by making small modification, which save some time and space cost.
public class DetectSquares {
    // list for unique int[] points
    List<int[]> list;
    // count map for points count
    Map<String, Integer> countMap;

    public DetectSquares() {
        list = new ArrayList<>();
        countMap = new HashMap<>();
    }

    public void add(int[] point) {
        String s = point[0] + "," + point[1];
        if (countMap.containsKey(s)) {
            countMap.put(s, countMap.get(s) + 1);
        } else {
            countMap.put(s, 1);
            list.add(point);
        }
    }

    public int count(int[] point) {
        int currentX = point[0];
        int currentY = point[1];
        int result = 0;
        for (int[] p : list) {
            if (p[0] != currentX && p[1] != currentY && Math.abs(p[0] - currentX) == Math.abs(p[1] - currentY)) {
                String otherPoint1 = currentX + "," + p[1];
                String otherPoint2 = p[0] + "," + currentY;
                result += countMap.getOrDefault(otherPoint1, 0) * countMap.getOrDefault(otherPoint2, 0) * countMap.get(p[0] + "," + p[1]);
            }
        }
        return result;
    }
}
