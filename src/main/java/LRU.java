import java.util.LinkedHashMap;
import java.util.Map;

public class LRU extends LinkedHashMap<Integer, Integer> {

    private  final int MAX_CAPACITY;

    public LRU(int capacity) {
        super(capacity, 0.75f, true);
        this.MAX_CAPACITY = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > MAX_CAPACITY;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }
}
