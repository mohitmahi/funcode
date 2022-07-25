import java.util.ArrayList;
import java.util.List;

/**
 * There are three kinds of characters, ‘L’, ‘R’, ‘X’.
 * Replacing XL with LX = move L to the left by one
 * Replacing RX with XR = move R to the right by one
 * If we remove all the X in both strings, the resulting strings should be the same.
 *
 * Additional observations:
 * Since a move always involves X, an L or R cannot move through another L or R.
 * Since an L can only move to the right, for each occurrence of L in the start string, its position should be to the same or to the left of its corresponding L in the end string.
 */
public class SwapLXR {

    public boolean canTransform(String start, String end) {
        if(start.length() != end.length()) return false;
        //check for string without X, it should be the same
        String startStr = start.replace("X", "");
        String endStr = end.replace("X", "");

        if(!(startStr.equals(endStr)) ) return false;
        List<Integer> startL = new ArrayList<>();
        List<Integer> startR = new ArrayList<>();
        List<Integer> endL = new ArrayList<>();
        List<Integer> endR = new ArrayList<>();


        for(int i=0; i<start.length(); i++){
            if(start.charAt(i) == 'L')
                startL.add(i);
            else if(start.charAt(i) == 'R')
                startR.add(i);
        }

        for(int i=0; i<end.length(); i++){
            if(end.charAt(i) == 'L')
                endL.add(i);
            else if(end.charAt(i) == 'R')
                endR.add(i);
        }

        // check L positions are correct
        for(int i=0; i<startL.size(); i++){
            if(startL.get(i) < endL.get(i))
                return false;
        }

        // check R positions are correct
        for(int i=0; i<startR.size(); i++){
            if(startR.get(i) > endR.get(i))
                return false;
        }

        return true;
    }
}

