import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * 3379. Transformed Array
 * Link : https://leetcode.com/problems/transformed-array/
 * -----------------------------------------------------------------------------
 *
 * Problem Summary:
 * ----------------
 * You are given an integer array `nums` of length `n`.
 *
 * For each index `i`, you need to compute:
 *
 *     result[i] = nums[ (i + nums[i]) mod n ]
 *
 * ⚠️ Important challenge:
 * -----------------------
 * - `nums[i]` can be NEGATIVE
 * - `(i + nums[i])` may go out of bounds
 * - You must correctly handle circular indexing
 *
 *
 * Goal:
 * -----
 * Construct and return the transformed array.
 *
 *
 * -----------------------------------------------------------------------------
 * KEY CHALLENGE: Handling NEGATIVE indices
 * -----------------------------------------------------------------------------
 *
 * Java's `%` operator can return NEGATIVE results.
 *
 * Example:
 * --------
 * (-1) % 5 = -1   ❌ (invalid index)
 *
 * Correct circular index formula:
 * --------------------------------
 * ((i + shift) % n + n) % n
 *
 * This guarantees:
 * - Final index is always in range [0, n-1]
 *
 *
 * -----------------------------------------------------------------------------
 * APPROACHES
 * -----------------------------------------------------------------------------
 *
 * Approach 1 (Commented Below):
 * -----------------------------
 * - Uses a large positive bias to avoid negative modulo
 * - Simple and clean
 * - Uses extra array → O(n) space
 *
 * Approach 2 (Implemented):
 * -------------------------
 * - IN-PLACE solution
 * - Uses BIT MANIPULATION to store:
 *      old value + new value inside the same integer
 * - O(1) extra space
 *
 * This approach is more advanced and interview-impressive.
 *
 *
 * -----------------------------------------------------------------------------
 * APPROACH 1: Bias + Extra Array (for reference)
 * -----------------------------------------------------------------------------
 *
 * Idea:
 * -----
 * Add a large multiple of `n` so the index never becomes negative.
 *
 * res[i] = nums[ (i + nums[i] + bias) % n ]
 *
 * bias must be >= max possible negative shift.
 *
 * Time Complexity : O(n)
 * Space Complexity: O(n)
 *
 * (Kept commented for conceptual clarity)
 *
 * -----------------------------------------------------------------------------
 */

class Solution {

    /*
     * -------------------------------------------------------------------------
     * APPROACH 1 (REFERENCE ONLY — NOT USED)
     * -------------------------------------------------------------------------
     *
     * public int[] constructTransformedArray(int[] nums) {
     *     int n = nums.length;
     *
     *     // Max negative value is -100 (based on constraints)
     *     // Bias ensures index never goes negative before modulo
     *     int bias = n * (100 / n) + n;
     *
     *     int[] res = new int[n];
     *
     *     for (int i = 0; i < n; i++) {
     *         res[i] = nums[(i + nums[i] + bias) % n];
     *     }
     *
     *     return res;
     * }
     *
     * -------------------------------------------------------------------------
     */

    /* =======================================================================
     * APPROACH 2: IN-PLACE TRANSFORMATION USING BIT MANIPULATION
     * =======================================================================
     */

    /**
     * Constructs the transformed array in-place.
     *
     * @param nums Input array (will be modified)
     * @return     Transformed array
     */
    public int[] constructTransformedArray(int[] nums) {

        int n = nums.length;

        /*
         * OFFSET is used to safely convert negative numbers
         * into non-negative values.
         *
         * Constraints ensure:
         *   nums[i] ∈ [-100, 100]
         *
         * Using offset = 128 (2^7) is sufficient.
         */
        int OFFSET = 1 << 7;      // 128

        /*
         * MASK extracts the LOWER 8 bits.
         * Used to retrieve the ORIGINAL value later.
         */
        int MASK = (1 << 8) - 1;  // 255

        /* ------------------------------------------------------------------
         * STEP 1: SHIFT ALL VALUES TO NON-NEGATIVE RANGE
         * ------------------------------------------------------------------
         *
         * After this step:
         *   nums[i] ∈ [0, 255]
         */
        for (int i = 0; i < n; i++) {
            nums[i] += OFFSET;
        }

        /* ------------------------------------------------------------------
         * STEP 2: ENCODE NEW VALUE INTO HIGHER BITS
         * ------------------------------------------------------------------
         *
         * Each nums[i] will store:
         *
         *   [ new_value (high bits) | old_value (low bits) ]
         *
         * This allows in-place computation without overwriting
         * values needed for future indices.
         */
        for (int i = 0; i < n; i++) {

            /*
             * Recover original value at index i.
             */
            int curr = nums[i] - OFFSET;

            /*
             * Compute circular index safely.
             */
            int idx = ((i + curr) % n + n) % n;

            /*
             * Extract the ORIGINAL value at idx
             * (lower 8 bits only).
             */
            int valueAtIdx = nums[idx] & MASK;

            /*
             * Store new value in higher bits.
             */
            nums[i] |= (valueAtIdx << 8);
        }

        /* ------------------------------------------------------------------
         * STEP 3: EXTRACT FINAL VALUES
         * ------------------------------------------------------------------
         *
         * Right shift removes old value.
         * Subtract OFFSET to restore original sign.
         */
        for (int i = 0; i < n; i++) {
            nums[i] = (nums[i] >> 8) - OFFSET;
        }

        /*
         * nums now contains the transformed array.
         */
        return nums;
    }
}
