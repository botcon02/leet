// https://leetcode.com/problems/search-in-rotated-sorted-array/

class RotatedArray {
    public int search(int[] nums, int target) {
        int pivot = nums[0];
        int left = 0;
        int right = nums.length - 1;
        if (target == pivot) {
            return 0;
        }
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int curr = nums[mid];
            if (target == curr) {
                return mid;
            } else if (pivot <= curr && target < curr && target > pivot) {
                right = mid - 1;
            } else if (pivot <= curr) {
                left = mid + 1;
            } else if (pivot >= curr && target < pivot && target > curr) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }   
}
