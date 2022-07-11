import com.google.common.util.concurrent.Monitor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public enum LRUCache {
    SINGLETON_CACHE(3);

    private Monitor mutex = new Monitor();

    final int capacity;
    final HashMap<Integer, Node> map;
    final AtomicInteger count = new AtomicInteger(0);
    final Node head, tail;

    LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = Node.builder().build();
        tail = Node.builder().build();
        head.next = tail;
        tail.prev = head;
    }

    public static void main(String[] args) {
        LRUCache cache = LRUCache.SINGLETON_CACHE;
        cache.addByKey(1, 10);
        cache.addByKey(2, 20);
        cache.addByKey(3, 30);
        System.out.println(cache.getByKey(1));
        System.out.println(cache.getByKey(4));
        System.out.println(cache.getByKey(3));
        cache.addByKey(4, 40);
        cache.addByKey(5, 50);
        System.out.println(cache.getByKey(4));
        System.out.println( cache.getByKey(5));
        System.out.println(cache.getByKey(2));
        System.out.println(cache.getByKey(1));
        System.out.println(cache.getByKey(3));
        cache.addByKey(6, 60);
        System.out.println(cache.getByKey(5));

    }
    public int getByKey(Integer key) {
       Node node =  map.get(key);
       moveToHead(node);
       return node == null ? -1 : node.value;
    }

    public void addByKey(Integer key, Integer value) {
        mutex.enter();
        if(map.get(key) == null) {
            Node node = Node.builder().key(key).value(value).build();
            count.addAndGet(1);
            attachToHead(node);
            map.put(key, node);

            if(count.get() > capacity) {
                map.remove(tail.prev.key);
                detachNode(tail.prev);
                count.decrementAndGet();
            }
        } else {
            Node node = map.get(key);
            node.setValue(value);
            moveToHead(node);
        }
        mutex.leave();
    }

    private void moveToHead(Node node) {
        detachNode(node);
        attachToHead(node);
    }

    private void attachToHead(Node node) {
        if(node == null) return;

        node.next = head.next;
        node.next.prev = node;
        node.prev = head;
        head.next = node;
    }

    private void detachNode(Node node) {
        if(node == null) return;

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    @Data
    @Builder
    static class Node {
        int key;
        int value;
        Node prev;
        Node next;
    }
}
