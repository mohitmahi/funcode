/*
A palindrome is a word that is the same when read left-to-right as well as right-to-left.  For example, “racecar” is a palindrome.

Given a string, find all of the palindrome substrings.

Example input: 

  racecar  ==> e 

  racedo ==> 

Example output:

   [“racecar”, “aceca”, “cec”, “r”, “a”, “c”, “e”, “c”, “a”, “r”]
*/

//. Pallindrome - Even  or  Odd  
// O(N) for each string ==> To get all substring for a string ==> N + 2 * N 

// 3N * N == n * N 

import java.io.*;
import java.util.*;

class Solution {
  public static void main(String[] args) {

    List<String> allPallindromList = getAllPallindromes("racecar");

    System.out.print(allPallindromList);

  }

  static List<String> getAllPallindromes(String input) {

    if(input == null || input.length() == 0) {
      return List.of();
    }

    List<String> result = new ArrayList<>();
    int index = 0;
    int length = input.length();
    
    while(index++ < length) {
      int left = index;
      int right = index;
      if(left < 0 || right >= length) break;
      while(input.charAt(left) == input.charAt(right)) {
        final String currentPallindrome = input.substring(left, right + 1);
        result.add(currentPallindrome);
        left --;
        right++;
        if(left < 0 || right >= length) break;
      }
    }
    return result;
  }
}
