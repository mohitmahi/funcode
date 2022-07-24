import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeMap {

    private static final String EMPTY_STR = "";
    private Map<String, TreeMap<Integer, String>> map = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        map.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) return EMPTY_STR;
        Map.Entry<Integer, String> e = map.get(key).floorEntry(timestamp);
        return e != null ? e.getValue() : EMPTY_STR;
    }
}