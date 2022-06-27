import lombok.Builder;

public class BST {

    public static void main(String[] args) {

        Node root = getBST();
        BinarySearchTree tree = BinarySearchTree.builder().root(root).build();

        System.out.println("IsPresent:: " + isNumberPresent(tree, 3));
        System.out.println("IsPresent:: " + isNumberPresent(tree, 2));
        System.out.println("IsPresent:: " + isNumberPresent(tree, 4));
        System.out.println("IsPresent:: " + isNumberPresent(tree, 6));
    }

    private static Node getBST() {
        //   3
        //  / \
        // 2   5
        //    /\
        //   4  6
        return Node.builder()
                .value(3)
                .left(Node.builder().value(2)
                        .build())
                .right(Node.builder().value(5)
                        .left(Node.builder().value(4).build())
                        .right(Node.builder().value(6).build())
                        .build())
                .build();
    }

    private static boolean isNumberPresent(BinarySearchTree tree, int number) {
        if (tree == null) return false;

        return DFSAndFind(tree.root, number); //(OLogN)
    }

    private static boolean DFSAndFind(Node root, int number) {
        if(root == null) return  false;
        if(root.value == number)  return true;
        return root.value > number ? DFSAndFind(root.left, number) : DFSAndFind(root.right, number);
    }

    @Builder
    static class BinarySearchTree {
        Node root;
    }

    @Builder
    static class Node {
        int value;
        Node left;
        Node right;
    }
}
