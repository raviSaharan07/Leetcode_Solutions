import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * 2433. Find The Original Array of Prefix XOR
 * Link : https://leetcode.com/problems/find-the-original-array-of-prefix-xor/
 * -----------------------------------------------------------------------------
 *
 * Problem Summary:
 * ----------------
 * You are given an integer array `pref` where:
 *
 *   pref[i] = arr[0] ^ arr[1] ^ ... ^ arr[i]
 *
 * (`^` denotes the bitwise XOR operation)
 *
 * Goal:
 * -----
 * Reconstruct and return the ORIGINAL array `arr`.
 *
 *
 * Key Observations:
 * -----------------
 * 1) XOR has a VERY important property:
 *
 *        a ^ a = 0
 *        a ^ 0 = a
 *
 * 2) XOR is REVERSIBLE:
 *
 *        If:
 *          pref[i] = pref[i-1] ^ arr[i]
 *
 *        Then:
 *          arr[i] = pref[i-1] ^ pref[i]
 *
 * 3) This means each element of `arr`
 *    can be derived independently using two prefix values.
 *
 *
 * Core Strategy:
 * --------------
 * PREFIX XOR REVERSAL
 *
 * - The first element:
 *      arr[0] = pref[0]
 *
 * - For every index i ≥ 1:
 *      arr[i] = pref[i-1] ^ pref[i]
 *
 *
 * Example Walkthrough:
 * --------------------
 * pref = [5, 2, 0, 3, 1]
 *
 * arr[0] = 5
 * arr[1] = 5 ^ 2 = 7
 * arr[2] = 2 ^ 0 = 2
 * arr[3] = 0 ^ 3 = 3
 * arr[4] = 3 ^ 1 = 2
 *
 * arr = [5, 7, 2, 3, 2]
 *
 *
 * Time Complexity:
 * ----------------
 * O(n)
 *
 * - Single pass through the array
 *
 *
 * Space Complexity:
 * -----------------
 * O(n)
 *
 * - Output array
 *
 * -----------------------------------------------------------------------------
 */

class Solution {

    /**
     * Reconstructs the original array from its prefix XOR array.
     *
     * @param pref Prefix XOR array
     * @return     Original array
     */
    public int[] findArray(int[] pref) {

        /*
         * Result array to store reconstructed values.
         */
        int[] result = new int[pref.length];

        /*
         * Base case:
         * The first element remains the same.
         */
        result[0] = pref[0];

        /*
         * Use XOR reversal property to compute remaining elements.
         */
        for (int i = 1; i < pref.length; i++) {

            /*
             * arr[i] = pref[i - 1] ^ pref[i]
             *
             * This works because:
             * pref[i] = pref[i - 1] ^ arr[i]
             */
            result[i] = pref[i - 1] ^ pref[i];
        }

        /*
         * Return the reconstructed original array.
         */
        return result;
    }
}
