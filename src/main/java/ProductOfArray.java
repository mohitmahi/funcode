public class ProductOfArray {
    // Given an array of integer, return an array such that arr[i] = arr[0] * .... arr[i-1] * arr[i+1] ... arr[n]
    // 2 * O(N) Time and O(1) space
    public int[] productExceptSelf(int[] nums) {
        int[] arr = new int[nums.length];
        //In first pass calculate the left product except self and in second calculate the right

        int right=1, left=1; // as starting/last number will have nothing of left/right so defauly 1
        for (int i = 0; i < nums.length; i++) {
            arr[i] = left; //assign I-1 (left) first, update 0th with 1
            left *= nums[i]; // then update left for next i+1
        }
        for(int i = nums.length - 1; i >= 0; i--) {
            arr[i] *= right; // for first run, arr[i] will not change.
            right *= nums[i];
        }
        return arr;
    }
}
