import lombok.Builder;
import lombok.Data;
import lombok.val;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class KthMax {

    public static void main(String[] args) {

        List<FileTuple> input = List.of(FileTuple.builder().fileName("A").size(10).collectionName("CollectionC").build(),
                FileTuple.builder().fileName("B").size(12).collectionName("CollectionA").build(),
                FileTuple.builder().fileName("C").size(14).collectionName("CollectionD").build(),
                FileTuple.builder().fileName("C").size(14).collectionName("CollectionB").build(),
                FileTuple.builder().fileName("C").size(14).collectionName("CollectionB").build(),
                FileTuple.builder().fileName("C").size(14).collectionName("CollectionB").build(),
                FileTuple.builder().fileName("C").size(14).collectionName("CollectionB").build(),
                FileTuple.builder().fileName("C").size(14).collectionName("CollectionB").build(),
                FileTuple.builder().fileName("C").size(14).collectionName("CollectionB").build(),
                FileTuple.builder().fileName("C").size(14).collectionName("CollectionB").build(),
                FileTuple.builder().fileName("D").size(15).collectionName("CollectionB").build());

        System.out.println(findTopNCollection(input, 2));
    }

    private static List<String> findTopNCollection(List<FileTuple> inputFiles, int Nth) {
        if(inputFiles == null || inputFiles.size() == 0) return List.of(); // // Invalid entry

        final Map<String, Long> collectionSizeMap = getCollectionSizeMap(inputFiles); // O(N) N ==> Number of Tuples.

        if(Nth < 1 || Nth > collectionSizeMap.size()) return List.of(); // Invalid entry

        final PriorityQueue<MinQueueNode> aMinHeap = getAMinHeap(Nth);

        for(val entry : collectionSizeMap.entrySet()) {  // O(K) K ==> Unique count of collection
            aMinHeap.add(MinQueueNode.builder().collectionName(entry.getKey()).size(entry.getValue()).build()); // O(LogK) K is the Kth Max we want to capture
            if(aMinHeap.size() > Nth) {
                aMinHeap.poll(); // O(LogK) K is the Kth Max we want to capture
            }
        }
        return getTopNCollectionList(aMinHeap);
    }

    private static List<String> getTopNCollectionList(PriorityQueue<MinQueueNode> aMinHeap) {
        List<String> nCollectionName = new ArrayList<>();
        while(aMinHeap.size() > 0) {
            nCollectionName.add(aMinHeap.poll().collectionName);
        }
        return nCollectionName;
    }

    private static Map<String, Long> getCollectionSizeMap(List<FileTuple> inputFiles) {
        final Map<String, Long> collectionMap = new HashMap<>();
        for(FileTuple tuple: inputFiles) {
            collectionMap.put(tuple.collectionName, collectionMap.getOrDefault(tuple.collectionName, 0L) + tuple.size);
        }
        return collectionMap;
    }

    private static PriorityQueue<MinQueueNode> getAMinHeap(int nth) {
        return new PriorityQueue<>(nth, Comparator.comparingLong((MinQueueNode n) -> n.size));
    }


    @Data
    @Builder
    static class FileTuple {
        String fileName;
        long size;
        String collectionName;
    }

    @Data
    @Builder
    static class MinQueueNode {
        long size;
        String collectionName;
    }
}
