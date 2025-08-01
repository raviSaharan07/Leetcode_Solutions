import java.util.ArrayList;
import java.util.List;

/**
 ** 34. Find First and Last Position of Element in Sorted Array
 ** Link: https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 **
 **
 ** Binary Search Problem
 ** Medium
 **
 ** Time Complexity: O(2*log(n))
 ** Space Complexity: O(1);
 **
 **
 ** This problem can also be done using upperBound and lowerBound,
 ** But there we have to check one condition for early return
 ** if(lb==n || arr[lb]!=target) return new int[] {-1,-1};
 ** So in this approach the result would be [lb,ub-1]
 **
 **
 ** In case we can't use the predefined upperBound and lowerBound functions,
 ** then we can we the below approach which is similar to those concepts
 ** with some tweaks
 */

public class Main {
    public static void main(String[] args) {
    }
}
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int lowerBound = findBound(nums,target,true);
        //Early Pruning
        if(lowerBound == -1) return new int[]{-1,-1};

        int upperBound = findBound(nums,target,false);

        return new int[]{lowerBound,upperBound};
    }

    private int findBound(int[] nums,int target,boolean isLowerSearching){
        int low = 0;
        int high = nums.length-1;
        int ans = -1;

        while(low<=high){
            int mid = low + ((high-low)/2);

            if(nums[mid]==target){
                ans = mid;
                if(isLowerSearching) high = mid -1;
                else low = mid+1;
            }else if (nums[mid]<target){
                low = mid+1;
            }else{
                high = mid-1;
            }
        }

        return ans;
    }
}
