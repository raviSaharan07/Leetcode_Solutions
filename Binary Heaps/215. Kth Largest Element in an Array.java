import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * 215. Kth Largest Element in an Array
 * Link :https://leetcode.com/problems/kth-largest-element-in-an-array/description/
 * -----------------------------------------------------------------------------
 *
 * Problem Summary:
 * ----------------
 * You are given an integer array `nums` and an integer `k`.
 *
 * Goal:
 * -----
 * Return the **k-th largest** element in the array.
 *
 * Important:
 * ----------
 * - The array does NOT need to be fully sorted.
 * - You must find the k-th largest element efficiently.
 *
 *
 * Two Optimal Approaches:
 * ----------------------
 * 1) MIN-HEAP (Priority Queue)
 * 2) QUICKSELECT (Partition-based selection)
 *
 * Both are standard, interview-favorite solutions.
 *
 * -----------------------------------------------------------------------------
 * APPROACH 1: MIN-HEAP (Binary Heap)
 * -----------------------------------------------------------------------------
 *
 * Key Idea:
 * ---------
 * - Maintain a MIN-HEAP of size `k`
 * - Heap always stores the **k largest elements seen so far**
 * - The smallest among them (heap root) is the k-th largest overall
 *
 * Why Min-Heap?
 * -------------
 * - Java PriorityQueue is a MIN-HEAP by default
 *
 *
 * Time Complexity:
 * ----------------
 * O(n log k)
 *
 * - Insert first k elements → O(k log k)
 * - For remaining elements → O((n - k) log k)
 *
 *
 * Space Complexity:
 * -----------------
 * O(k)
 *
 * -----------------------------------------------------------------------------
 * APPROACH 2: QUICKSELECT (Optimized Selection Algorithm)
 * -----------------------------------------------------------------------------
 *
 * Key Idea:
 * ---------
 * - Based on QuickSort partitioning
 * - We do NOT sort the entire array
 * - We only recurse into the part that contains the answer
 *
 * Trick:
 * ------
 * - k-th largest = (n - k)-th smallest (0-indexed)
 *
 *
 * Average Time Complexity:
 * ------------------------
 * O(n)
 *
 * Worst Case:
 * -----------
 * O(n²) (rare, but possible if bad pivots chosen)
 *
 *
 * Space Complexity:
 * -----------------
 * O(1) (in-place)
 *
 * -----------------------------------------------------------------------------
 */

class Solution {

    /* =======================================================================
     * APPROACH 1: MIN-HEAP (Binary Heap)
     * =======================================================================
     */

    /**
     * Finds the k-th largest element using a Min-Heap.
     *
     * @param nums Input array
     * @param k    The k-th largest position
     * @return     k-th largest element
     */
    public int findKthLargest_heap(int[] nums, int k) {

        /*
         * Min-Heap of size at most k.
         * The smallest element in the heap
         * represents the current k-th largest.
         */
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        /*
         * Step 1:
         * Insert first k elements into the heap.
         */
        for (int i = 0; i < k; i++) {
            minHeap.offer(nums[i]);
        }

        /*
         * Step 2:
         * For remaining elements:
         * - If current element is larger than heap root,
         *   it deserves to be in top k
         */
        for (int i = k; i < nums.length; i++) {
            if (nums[i] > minHeap.peek()) {
                minHeap.poll();          // remove smallest among top k
                minHeap.offer(nums[i]);  // insert larger element
            }
        }

        /*
         * Heap root now holds the k-th largest element.
         */
        return minHeap.peek();
    }

    /* =======================================================================
     * APPROACH 2: QUICKSELECT
     * =======================================================================
     */

    /**
     * Finds the k-th largest element using QuickSelect.
     *
     * @param nums Input array
     * @param k    The k-th largest position
     * @return     k-th largest element
     */
    public int findKthLargest_quickSelect(int[] nums, int k) {

        int n = nums.length;

        /*
         * Convert k-th largest to index of k-th smallest.
         *
         * Example:
         * n = 6, k = 2
         * → 2nd largest = index (6 - 2) = 4 (0-based)
         */
        int targetIndex = n - k;

        int left = 0;
        int right = n - 1;

        /*
         * Iterative QuickSelect loop.
         */
        while (left <= right) {

            /*
             * Partition the array and get pivot index.
             */
            int pivotIndex = partition(nums, left, right);

            if (pivotIndex == targetIndex) {
                /*
                 * Found the k-th largest element.
                 */
                return nums[pivotIndex];
            } else if (pivotIndex < targetIndex) {
                /*
                 * Target lies in the RIGHT partition.
                 */
                left = pivotIndex + 1;
            } else {
                /*
                 * Target lies in the LEFT partition.
                 */
                right = pivotIndex - 1;
            }
        }

        /*
         * This line should never be reached.
         */
        return -1;
    }

    /**
     * Partition function (Lomuto Partition Scheme).
     *
     * It places the pivot in its correct sorted position
     * and ensures:
     *   - Elements ≤ pivot on the left
     *   - Elements > pivot on the right
     */
    private int partition(int[] nums, int left, int right) {

        /*
         * Choose the rightmost element as pivot.
         */
        int pivotValue = nums[right];

        /*
         * `i` marks the boundary for elements ≤ pivot.
         */
        int i = left;

        /*
         * Traverse and rearrange elements.
         */
        for (int j = left; j < right; j++) {
            if (nums[j] <= pivotValue) {
                swap(nums, i, j);
                i++;
            }
        }

        /*
         * Place pivot in its final position.
         */
        swap(nums, i, right);

        return i;
    }

    /**
     * Utility function to swap two elements.
     */
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
