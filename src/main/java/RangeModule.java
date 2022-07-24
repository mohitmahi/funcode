import java.util.Map;
import java.util.TreeMap;

public class RangeModule {
    TreeMap<Integer, Integer> first = new TreeMap<>();

    public void addRange(int left, int right) {
        while (true) {
            // Try to merge with existing ones.
            Map.Entry<Integer, Integer> entry = first.floorEntry(right);
            if (entry == null || entry.getValue() < left)
                break;
            left = Math.min(left, entry.getKey());
            right = Math.max(right, entry.getValue());
            first.remove(entry.getKey());
        }
        first.put(left, right);
    }

    public boolean queryRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = first.floorEntry(left);
        return entry != null && entry.getValue() >= right;
    }

    public void removeRange(int left, int right) {
        // Add the range first, to make the code simplier.
        addRange(left, right);
        Map.Entry<Integer, Integer> entry = first.floorEntry(left);
        first.remove(entry.getKey());
        if (entry.getKey() < left)
            first.put(entry.getKey(), left);
        if (entry.getValue() > right)
            first.put(right, entry.getValue());
    }
}