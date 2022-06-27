import java.util.List;

public class LongestMountain {

    //  arr.length >= 3
    //  There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
    //  arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
    //  arr[i] > arr[i + 1] > ... > arr[arr.length - 1]

    // Given an integer array arr, return the length of the longest subarray,
    // which is a mountain. Return 0 if there is no mountain subarray.


    // Good Question to ask :
    //  Flat level at bottom surface or at peak (1,4,5,5,5,2,0,0,0,3)
    // Incomplete Mountain:  starting with downHill, Uphill with no downhill at end (5, 6, 4) (2,4,6,7)


    public static void main(String[] args) {
        final List<Integer> input1 = List.of(2, 1, 4, 7, 3, 2, 1); //  \/\
        System.out.println("\nLongest:: " + findLongestMountain(input1)); // ==> 6

        final List<Integer> input2 = List.of(2, 1, 4, 7, 3, 2, 4); //  \/\/
        System.out.println("\nLongest:: " + findLongestMountain(input2)); // ==> 5

        final List<Integer> input3 = List.of(7, 3, 2, 4); //  \/
        System.out.println("\nLongest:: " + findLongestMountain(input3)); // ==> 0
    }

    static int findLongestMountain(List<Integer> input) {
        int longestLength = 0;
        if (input.size() < 3) return -1; //invalid Input

        int currentStep = 0;
        int index = 0;
        int totalSteps = input.size();

        boolean goingDownhill = false;

        while (index < totalSteps - 1 && input.get(index) >= input.get(index + 1))
            index++; //remove invalid starting downhill if any

        while (index < totalSteps - 1) {

            // Reset currentStep and record last run
            if (input.get(index) <= input.get(index + 1) && goingDownhill) {
                if(goingDownhill) {
                    goingDownhill = false;
                    longestLength = Math.max(longestLength, currentStep + 1); //record last run
                    currentStep = 0;
                }
            }

            // Start Uphill
            while (index < totalSteps - 1 && input.get(index) <= input.get(index + 1)) {
                currentStep++;
                index++;
            }

            // Start Downhill
            if (index < totalSteps - 1 && input.get(index) > input.get(index + 1)) {
                goingDownhill = true;
            }
            while (index < totalSteps - 1 && input.get(index) > input.get(index + 1)) {
                currentStep++;
                index++;
            }

            //No more uphill, so record longestLength
            if(index == totalSteps - 1 && goingDownhill) {
                return Math.max(longestLength, currentStep + 1);
            }
        }
        return longestLength;
    }
}
