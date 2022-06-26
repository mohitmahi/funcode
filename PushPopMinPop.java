import java.util.LinkedList;
import java.util.PriorityQueue;

public class PushPopMinPop {

    //(Memory: 2 * O(N))
    //Time push() O(1) + O(logN)
    //Time pop()  O(1) + O(N) (worst case will O(N) for removing an item from PQ)
    //Time getMin() O(1)
    //Time popMin() O(1)(DLL) + O(logN)(PQ)  [*tricky*]
    //Duplicates => Corner Case.

    //pop 3 cases:
    // 1. Peek of both LL and PQ are same => O(1)LL + O(logN)PQ
    // 2. popMin & peek of LL is not min  => O(N)LL + O(logN)PQ
    // 2. pop & peek of PQ is not peek of LL  => O(1)LL + O(N)PQ

    final LinkedList<Integer> stack = new LinkedList<>();                        // DLL to allow using as stack but allow to remove any item in O(1).
    final PriorityQueue<Integer> minHeap = new PriorityQueue<>(Integer::compare);  //Min Heap to hold all elements but keep min at root, and re-balance after add/delete

    public static void main(String[] args) {
        PushPopMinPop stackOp = new PushPopMinPop();

        stackOp.push(3);   //Min => 3  [3]
        stackOp.push(1);   //Min => 1  [3,1]
        stackOp.push(2);   //Min => 1  [3,1,2]
        stackOp.push(4);   //Min => 1  [3,1,2,4]
        stackOp.push(2);   //Min => 1  [3,1,2,4,2]
        stackOp.printCurrentView();

        stackOp.pop(); //Remove last 2  //Min => 1     [3,1,2,4]
        stackOp.printCurrentView();

        stackOp.pop(); //Remove last 4  //Min => 1     [3,1,2]
        stackOp.printCurrentView();

        System.out.println(stackOp.popMin()); //Remove last 1  //Min => 2     [3,2]
        System.out.println(stackOp.min());    //Min => 2
        stackOp.printCurrentView();

        System.out.println(stackOp.popMin()); //Remove last 2  //Min => 3     [3]
        System.out.println(stackOp.min());    //Min => 3
        stackOp.printCurrentView();
        
        stackOp.clear();
    }

    void pop() {
        if ((minHeap.size()) > 0) {
            if(minHeap.peek().equals(stack.peek())) {
               minHeap.poll(); // O(logN)
            } else {
                minHeap.remove(stack.peek()); // O(N)
            }
        }
        stack.pop(); // O(1)
    }

    void push(Integer input) {
        stack.push(input); // O(1)
        minHeap.add(input);  // O(logN)
    }

    Integer min() {
        return minHeap.isEmpty() ? Integer.MIN_VALUE : minHeap.peek(); // O(1)
    }

    Integer popMin() {
        if(minHeap.isEmpty()) return Integer.MIN_VALUE;
        stack.remove(minHeap.peek()); // O(N)
        return minHeap.poll();            // O(logN)
    }

    void clear() {
        minHeap.clear();
        stack.clear();
    }

    void printCurrentView() {
        System.out.println("PQ: " + minHeap);
        System.out.println("DLL: " + stack);
    }
}
