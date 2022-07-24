import java.util.HashMap;

//Approach 2: Trie based approach less space as the paths sharing common prefixes can be represented by a common branch in the tree.
// O(T) to add a path to the trie if it contains T components. while get also will be O(T) instead of O(1)
// https://leetcode.com/problems/design-file-system/solution/

public class FileSystem {
    HashMap<String, Integer> paths;

    public FileSystem() {
        this.paths = new HashMap<String, Integer>();
    }

    public boolean createPath(String path, int value) {

        // Step-1: basic path validations
        if (path.isEmpty() || (path.length() == 1 && path.equals("/")) || this.paths.containsKey(path)) {
            return false;
        }

        int delimIndex = path.lastIndexOf("/"); //Time Complexity: O(M), where M is the length of path
        String parent = path.substring(0, delimIndex); //Time Complexity: O(M), where M is the length of path

        // Step-2: if the parent doesn't exist. Note that "/" is a valid parent.
        if (parent.length() > 1 && !this.paths.containsKey(parent)) {
            return false;
        }

        // Step-3: add this new path and return true.
        this.paths.put(path, value);
        return true;
    }

    public int get(String path) { //O(1)
        return this.paths.getOrDefault(path, -1);
    }
}
