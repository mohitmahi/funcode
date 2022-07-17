import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

public class MaxPerLevelProduct {

    public static void main(String[] args) {
        final TreeNode root = TreeNode.builder().value(10)
                .left(TreeNode.builder()
                        .value(3)
                        .left(TreeNode.builder()
                                .value(7)
                                .build())
                        .right(TreeNode.builder()
                                .value(10)
                                .build())
                        .build())
                .right(TreeNode.builder()
                        .value(4)
                        .left(TreeNode.builder()
                                .value(12)
                                .build())
                        .right(TreeNode.builder()
                                .value(9)
                                .build())
                        .build())
                .build();

        System.out.println(findMaxProduct(root));
    }

    private static int findMaxProduct(TreeNode root) {
        final Queue<TreeNode> queue = new LinkedList<>();
        if(root == null) return -1;

        queue.add(root);
        int result = 1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            int max = Integer.MIN_VALUE;
            while (qSize-- > 0) { //loop only item at current Level  "L"
                final TreeNode current = queue.poll();
                max = Math.max(max, current.value);

                if(current.left != null) queue.add(current.left); //add L+1 level item
                if(current.right != null) queue.add(current.right); //add L+1 level item
            }
            result *= max;
        }
        return result;
    }


    @Builder
    @Data
    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
    }
}
