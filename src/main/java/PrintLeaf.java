import lombok.Builder;

/*** Find Leaves of Binary Tree {Group them by Heights}
Map<Integer, List<Integer>> heightToNodes;
 int maxheight;

public List<List<Integer>> findLeaves(TreeNode root) {

        maxheight = 0;
        heightToNodes = new HashMap<>();
        getHeight(root);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 1; i <= maxheight; i++) {
        result.add(heightToNodes.get(i));
        }

        return result;
        }

private int getHeight(TreeNode cur) {

        if (cur == null) {
        return 0;
        }

        int curHeight = Math.max(getHeight(cur.left), getHeight(cur.right)) + 1;
        if (!heightToNodes.containsKey(curHeight)) {
        heightToNodes.put(curHeight, new ArrayList<>());
        }
        heightToNodes.get(curHeight).add(cur.val);
        maxheight = Math.max(maxheight, curHeight);

        return curHeight;
        }
**/
public class PrintLeaf {

    public static void main(String[] args) {

        final Node root = getATree();
        printAllLeaf(root);

    }

    private static void printAllLeaf(Node root) {
        if(root == null) return;

        if(root.left == null && root.right == null) {
            System.out.print(root.value);
        }
        if(root.left != null) {
            printAllLeaf(root.left);
        }
        if(root.right != null) {
            printAllLeaf(root.right);
        }
    }

    private static Node getATree() {
        //   3
        //  / \
        // 2   6
        //    /\
        //   4  1
        return Node.builder()
                .value(3)
                .left(Node.builder().value(2)
                        .build())
                .right(Node.builder().value(6)
                        .left(Node.builder().value(4).build())
                        .right(Node.builder().value(1).build())
                        .build())
                .build();
    }

    @Builder
    static class BinaryTree {
        Node root;
    }

    @Builder
    static class Node {
        int value;
        Node left;
        Node right;
    }
}
