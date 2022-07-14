public class LCA {
    /**
     *
     * Do DFS from root to both S & D, and then find common intersection point. (2(O)N)
     *
     * // Function to get LCA of given two nodes
     *     TreeNode* getLCA(TreeNode* root, int start, int dest) {
     *         if(!root) return NULL;
     *
     *         if(root->val == start || root->val == dest) return root;
     *
     *         TreeNode* left = getLCA(root->left, start, dest);
     *         TreeNode* right = getLCA(root->right, start, dest);
     *
     *         if(left && right) return root;
     *
     *         return left ? left : right;
     *     }
     */
}
