import lombok.Builder;

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
