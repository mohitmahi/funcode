import java.util.HashMap;
import java.util.Stack;

public class BalancedString {

    final static HashMap<Character, Character> validComplementMap = new HashMap<>();
    static {
        validComplementMap.put('(', ')');
        validComplementMap.put('{', '}');
        validComplementMap.put('[', ']');
    }

    enum OPEN_TYPE {
        A("("),
        B("{"),
        C("[");

        public final String value;
        OPEN_TYPE(String s) {
            this.value = s;
        }
    }

    enum CLOSE_TYPE {
        A(")"),
        B("}"),
        C("]");

        public final String value;
        CLOSE_TYPE(String s) {
            this.value = s;
        }
    }

    public static void main(String[] args) {
        System.out.println(isBalancedString("(){}"));
        System.out.println(isBalancedString("(){[]}"));
        System.out.println(isBalancedString("(){][}"));
        System.out.println(isBalancedString("("));
        System.out.println(isBalancedString(")("));
        System.out.println(isBalancedString("((({{{[[[("));
    }


    static boolean isBalancedString(final String input) {

        //O (1) ==> HashTable (hash) => AVL Tree (equals) (before java 8 DLL)

        if(input == null || input.length() <= 1 || input.length() % 2 != 0) return false;

        final Stack<Character> stack = new Stack<>();
        final int len = input.length();
        int i=0;
        while (i < len) {
            final char currentChar = input.charAt(i);
            if(validComplementMap.containsKey(currentChar)) {
                stack.push(currentChar);
            } else if(validComplementMap.containsValue(currentChar)) {
                if(!stack.isEmpty() && validComplementMap.get(stack.peek()) == currentChar) {
                    stack.pop();
                } else {
                    return false;
                }
            }
            i++;
        }
        return stack.empty(); // Empty refers to balanced string
    }

    // CustomHashSet is a CustomHashMap with no value.

    static class CustomHashMap<T> {
        int maxSize;

        // Java object -> hashCode and equals()
        // Hashing Function using hashCode as input ==> Bucket  (there will be max maxSize bucket)

        // boolean put(T key, T value); // Find the bucket for given key and then add the value to DLL(O)(1)- O(N)/AVL(LogN)
        // True -> Created / False -> Updated

        // get(T key) -> T

        // int size() -> int

        // void clear() -->

        //putIfAbsent(T key, Consumer<T> consumer)

        //ComputeIfAbsent()

    }
}
