import lombok.Builder;

public class BST {

    public static void main(String[] args) {
        //   3
        //  /\
        // 2  4
        Node root_left = Node.builder().value(2).build();
        Node root_right = Node.builder().value(4).build();
        Node root = Node.builder()
                .value(3)
                .left(root_left)
                .right(root_right)
                .build();
        BinarySearchTree tree = BinarySearchTree.builder().root(root).build();

        System.out.println("IsPresent:: " + isNumberPresent(tree, 3));
        System.out.println("IsPresent:: " + isNumberPresent(tree, 2));
        System.out.println("IsPresent:: " + isNumberPresent(tree, 4));
        System.out.println("IsPresent:: " + isNumberPresent(tree, 5));
    }

    private static boolean isNumberPresent(BinarySearchTree tree, int number) {
        if (tree == null) return false;

        return DFSAndFind(tree.root, number);
    }

    private static boolean DFSAndFind(Node root, int number) {
        if(root == null) return  false;

        if(root.value == number)  return true;

        if(root.value > number) {
            return DFSAndFind(root.left, number);
        } else {
            return DFSAndFind(root.right, number);
        }
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
