import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class isAnagram {
    public static void main(String[] args) {
        System.out.println(isAnagram("moh", "hom"));
        System.out.println(groupAnagrams(new String[]{"moh", "hom","ogm","omg","okm"}));
    }

        public static boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) return false;

            int[] store = new int[26];

            for (int i = 0; i < s.length(); i++) {
                store[s.charAt(i) - 'a']++;
                store[t.charAt(i) - 'a']--;
            }

            for (int n : store) if (n != 0) return false;

            return true;
        }

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        if (strs.length == 0) return res;
        HashMap<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] hash = new char[26];
            for (char c : s.toCharArray()) {
                hash[c - 'a']++;
            }
            String key = new String(hash);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        res.addAll(map.values());
        return res;
    }
    }

