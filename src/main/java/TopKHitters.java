import com.google.common.util.concurrent.RateLimiter;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKHitters {
    public static void main(String[] args) {
        String[] input = {"A", "A", "B", "B", "D", "D", "B","B", "D", "D","D", "D", "D","D", "D"};
        List<HeaveHitters> result = topK(input, 1);
        System.out.println(result);
        RateLimiter.create(1);
    }

    public static List<HeaveHitters> topK(String[] events, int k) {
        final HashMap<String, Integer> frequencyTable = new HashMap<>();
        for(String event: events) {
            frequencyTable.put(event, frequencyTable.getOrDefault(event, 0) + 1);
        }

        PriorityQueue<HeaveHitters> minHeap = new PriorityQueue<>(k, Comparator.comparingInt(HeaveHitters::getFrequency));

        for(Map.Entry<String, Integer> entry: frequencyTable.entrySet()) {
            minHeap.offer(HeaveHitters.builder()
                    .frequency(entry.getValue())
                    .id(entry.getKey())
                    .build());
            if(minHeap.size() > k) {
                minHeap.remove();
            }
        }
        List<HeaveHitters> result = new ArrayList<>();
        while (minHeap.size() > 0) {
            result.add(minHeap.poll());
        }
        return result;
    }

    @Data
    @Builder
    static class HeaveHitters {
        private final int frequency;
        private final String id;
    }
}
