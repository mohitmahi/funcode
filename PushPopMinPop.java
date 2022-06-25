import java.util.LinkedList;
import java.util.PriorityQueue;

public class PushPopMinPop {

    // push() O(1) + O(logN)
    // pop()  O(1) + O(logN)
    // getMin() O(1)
    // popMin() O(1) + O(logN) * tricky *

    final LinkedList<Integer> mainStack = new LinkedList<>();                        // DLL to allow using as stack but allow to remove any item in O(1).
    final PriorityQueue<Integer> minStack = new PriorityQueue<>(Integer::compare);  //Min Heap to hold all elements but keep min at root, and re-balance after add/delete

    public static void main(String[] args) {
        PushPopMinPop stackOp = new PushPopMinPop();

        stackOp.push(3);
        stackOp.push(1);
        stackOp.push(2);
        stackOp.push(4);
        stackOp.push(2);

        stackOp.pop();
        stackOp.pop();
        
        stackOp.popmin();
        stackOp.popmin();

        System.out.println(stackOp.min());

    }

    void pop() {
        if ((minStack.size()) > 0 && minStack.peek().equals(mainStack.peek())) {
            minStack.poll();
        }
        mainStack.pop();
    }

    void push(Integer input) {
        mainStack.push(input);
        minStack.add(input);
    }

    int min() {
        return minStack.peek();
    }

    int popmin() {
        if(minStack.isEmpty()) return Integer.MIN_VALUE;
        mainStack.remove(minStack.peek());
        return minStack.poll();
    }
}
