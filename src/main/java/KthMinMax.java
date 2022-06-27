
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Mohit
 */
public class KthMinMax {


    public enum TelemetryType { unknown, logs, metrics, events, traces, histograms, annotations };

    public enum TELEMETRY_TYPE {
        LOGS("logs"),
        METRICS("metrics"),
        EVENTS("events");

        public final String value;
        TELEMETRY_TYPE(String value) {
            this.value = value;
        }
    }
    public static void main(String[] args) {
        /*String key =
                Stream.of("", "subService", "ou")
                        .filter(s -> !StringUtils.isBlank(s))
                        .collect(Collectors.joining(":"));
        System.out.print("KEY:" + key);*/

       /* Set<String> stringSet = ImmutableSet.of("a", "v");
        System.out.print(stringSet.contains("a"));
        System.out.print(stringSet.contains(""));
        System.out.print(stringSet.contains(null));*/

        //System.out.print(Optional.ofNullable(null).orElse(Integer.valueOf("3")));

        final List<Integer> inputNumbers = List.of(1,4,2,6,9,15,22,12);
        final PriorityQueue<Integer> minHeap = getAMinHeap(inputNumbers);
        final PriorityQueue<Integer> maxHeap = getAMaxHeap(inputNumbers);


        System.out.print("MIN_HEAP-Increasing::");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + "=>");
        }
        System.out.print("\nMAX_HEAP-Decreasing::");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + "=>");
        }

        printKthMin(inputNumbers, 3);
        printKthMaX(inputNumbers, 3);
    }

    private static void printKthMaX(List<Integer> inputNumbers, int kth) {
        final PriorityQueue<Integer> minHeap = getAMinHeap(kth);
        addKItemsToHeap(inputNumbers, minHeap, kth);
        System.out.println("\nKth-" + kth + " max numbers is: " + minHeap.peek());
    }

    private static void printKthMin(List<Integer> inputNumbers, int kth) {
        final PriorityQueue<Integer> maxHeap = getAMaxHeap(kth);
        addKItemsToHeap(inputNumbers, maxHeap, kth);
        System.out.println("\n\nKth-" + kth + " min numbers is: " + maxHeap.peek());
    }

    private static void addKItemsToHeap(List<Integer> inputNumbers, PriorityQueue<Integer> minHeap, int kth) {
        inputNumbers.forEach(integer -> {
            minHeap.add(integer);
            if(minHeap.size() > kth) minHeap.poll();
        });
    }

    private static PriorityQueue<Integer> getAMaxHeap(List<Integer> inputNumbers) {
        final PriorityQueue<Integer> maxHeap = getAMaxHeap(inputNumbers.size());
        maxHeap.addAll(inputNumbers);
        return maxHeap;
    }

    private static PriorityQueue<Integer> getAMaxHeap(int size) {
        return new PriorityQueue<>(size, (Integer o1, Integer o2) -> -o1.compareTo(o2));
    }

    private static PriorityQueue<Integer> getAMinHeap(List<Integer> inputNumbers) {
        final PriorityQueue<Integer> minHeap =getAMinHeap(inputNumbers.size());
        minHeap.addAll(inputNumbers);
        return minHeap;
    }

    private static PriorityQueue<Integer> getAMinHeap(int size) {
        return new PriorityQueue<>(size, Integer::compareTo);
    }
}
