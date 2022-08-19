import java.io.*;
import java.util.*;
public class BrowserHistory {
    /*Write a simple browser history functionality for a single tab browser.
Starting with the home page, allow for browsing back and forward with variable depth.
Your code should have the following methods:

void visit(string destinationUrl)
 - goes to a given URL
 - starts new forward history

string back(int steps)
 - goes back in browsing history by the number of steps
 - returns the URL at that point in history

string forward(int steps)
 - goes forward in history by the number of steps
 - returns the URL at that point in history
 - note: user can only go forward after coming back in history first

string home()
 - goes back to START_PAGE (clears history)
 - returns the START_PAGE URL

 Examples test cases to ask
#  visit(a) ... visit(e) -> user should see 'e'
#  back(2) -> user should see 'c'
#  visit(f) -> user should see 'f' (history is [homepage,a,b,c,f])
*/

// visit(a) => visit(a) ==> Already on A
    // A => B ==> C ==>D ==> E
//   0     1     2   3     4
//  5 - 2 ==> 3 (2)


        final static LinkedList<String> history = new LinkedList<>(); //DLL
        static Integer pointer = 1; //default
        final static String START_PAGE_URL = "START_PAGE";
        final static String NOT_ALLOWED = "INVALID_SCROLL";

        public static void main(String[] args) {
            history.add(START_PAGE_URL);

            visit("A");
            visit("B");
            visit("C");
            visit("D");
            visit("E");
            System.out.println(back(2));
            //System.out.println(forward(12));
            visit("F");
            System.out.println(history);// [START_PAGE, A, B, C, F, D, E]

        }


        static void visit(String destinationUrl) {
            if(destinationUrl == null || destinationUrl.length() == 0) return; //Invalid URL
            if(!history.isEmpty() && history.peek().equals(destinationUrl)) return;  // visit(a) => visit(a) ==> Already on A

            history.add(pointer, destinationUrl); // >> shift right
            pointer += 1;
        }

        static String back(int steps) {
            if(history.size() < steps) {
                pointer = 1;
                return history.get(0);
            }
            pointer = history.size() - steps;
            return history.get(pointer - 1);
        }

        static String forward(int steps) {
            if(pointer == history.size() + 1) return history.peek();

            if(pointer + steps >= history.size()) {
                pointer = history.size();
                return history.peek();
            } else {
                pointer += steps;
                return history.get(pointer - 1);
            }
        }

        static String home() {
            history.clear();
            history.add(START_PAGE_URL);
            return START_PAGE_URL;
        }
    }

}
