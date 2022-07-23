import org.junit.Assert;

public class FindNextPalindrome {
    public static void main(String[] args) {
        Assert.assertEquals(2, findNextPalindrome(1));
        Assert.assertEquals(11,findNextPalindrome(9));

        Assert.assertEquals(1001,findNextPalindrome(999)); //odd with one more digit
        Assert.assertEquals(929,findNextPalindrome(923)); //odd without adding one to Left half
        Assert.assertEquals(131,findNextPalindrome(123)); //odd with adding one to Left half

        Assert.assertEquals(10001,findNextPalindrome(9999)); //even with one more digit
        Assert.assertEquals(3003,findNextPalindrome(2999)); //even with adding one to Left half
        Assert.assertEquals(2002,findNextPalindrome(2000)); //even without adding one to Left half
    }

    private static int findNextPalindrome(int input) {
        final String inputS = String.valueOf(input);

        if (input < 9) return input + 1;
        if (input == 9) return  11; //corner case

        final int size = inputS.length();
        boolean isEven = size%2 == 0;
        final String leftHalf =  inputS.substring(0, size / 2);
        StringBuilder temp = new StringBuilder(leftHalf);
        final String rightHalf = String.valueOf(temp.reverse());

        int newNumber;
        if(isEven) {
            newNumber = Integer.parseInt(leftHalf + rightHalf);
        } else {
            newNumber = Integer.parseInt(leftHalf + inputS.charAt(size / 2) + rightHalf);
        }
        if (newNumber > input) { //2000 => 20-02
            return newNumber;
        }

        final String leftAddedOne;
        if(isEven) {
            leftAddedOne = String.valueOf(Integer.parseInt(leftHalf) + 1); //2999 => 29+1=> 30+03
        } else {
            leftAddedOne = String.valueOf(Integer.parseInt(leftHalf + inputS.charAt(size / 2)) + 1); //123 => 1(2+1) => 131
        }
        temp = new StringBuilder(leftAddedOne);
        int diff = leftAddedOne.length() - leftHalf.length();
        while(diff-- > 0) { //99 => 9-9 => 10=> 10-1X(Deleted) => 101
            temp.deleteCharAt(temp.length() - 1);
        }
        final String rightHalfNew = String.valueOf(temp.reverse());
        return Integer.parseInt(leftAddedOne + rightHalfNew);
    }
}
