

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
//Time complexity should be O(2^N) since at each instruction we can choose A or R. Actual runtime should be lower than O(2^N) because of pruning
public class RaceCar {

    public static void main(String[] args) {
        System.out.println("Minimum Step will be:: " + findMinimumStep(6));
    }

    private static int findMinimumStep(int target) {
        final Queue<QItem> queue =  new LinkedList<>();
        final Set<Node> set = new HashSet<>();

        final Node startNode = new RaceCar.Node(0,1);

        final QItem firstItem = getAQueueItem(0, startNode, "");
        queue.add(firstItem);

        System.out.println("[stepCount, node.speed , node.position]");
        while (!queue.isEmpty()) { //BFS
            final QItem currentQItem = queue.poll();
            System.out.println(currentQItem.toString());

            if(currentQItem.node.position == target) return currentQItem.stepCount;

            if(set.contains(currentQItem.node)) continue;

            set.add(currentQItem.node); //mark as visited

            if(isReverseRequired(target, currentQItem)) {
                // need to reverse
                final int newSpeed = currentQItem.node.speed > 0 ? -1 : 1;
                queue.add(getAQueueItem(currentQItem.stepCount + 1,
                        new RaceCar.Node(currentQItem.node.position,newSpeed), currentQItem.path + "->R"));
            }
           //Move forward
            queue.add(getAQueueItem(
                    currentQItem.stepCount + 1, new RaceCar.Node(currentQItem.node.position + currentQItem.node.speed,2 * currentQItem.node.speed), currentQItem.path + "->A"));

        }
        return -1;
    }

    private static QItem getAQueueItem(int stepCount, Node position, String path) {
        return new RaceCar.QItem(position,stepCount, path);
    }

    private static boolean isReverseRequired(int target, QItem currentQItem) {
        return (currentQItem.node.position + currentQItem.node.speed > target && currentQItem.node.speed > 0) ||
                (currentQItem.node.position + currentQItem.node.speed < target && currentQItem.node.speed < 0);
    }


    static class QItem{
        private  Node node;
        private  int stepCount;
        private String path;

        public QItem(Node position, int stepCount, String path) {
            this.node = position;
            this.stepCount = stepCount;
            this.path = path;
        }

        @Override
        public String toString() {
            return "[" + stepCount + ", " + node.speed + ", " + node.position + "==>" + path + "]";
        }
    }


    static class Node{
        private final int position;
        private final int speed;

        public Node(int i, int i1) {
            this.position = i;
            this.speed = i1;
        }
    }
}
