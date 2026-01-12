import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * 3806. Maximum Bitwise AND After Increment Operations
 * Link : https://leetcode.com/problems/maximum-bitwise-and-after-increment-operations/description/
 * -----------------------------------------------------------------------------
 *
 * Problem Summary:
 * ----------------
 * You are given:
 *  - An integer array `nums`
 *  - An integer `k` (maximum allowed increment operations)
 *  - An integer `m` (size of subset)
 *
 * In one operation, you may increase any element of `nums` by 1.
 *
 * Goal:
 * -----
 * Choose ANY subset of size `m` after performing at most `k` increments
 * such that the BITWISE AND of the chosen `m` elements is maximized.
 *
 *
 * Key Observations:
 * -----------------
 * 1) Bitwise AND is determined bit-by-bit.
 * 2) A bit can be `1` in the AND result ONLY IF all `m` chosen numbers
 *    have that bit set.
 * 3) Higher bits contribute more to the value, so we try to set them first.
 *
 *
 * Core Strategy:
 * --------------
 * GREEDY + BIT MANIPULATION
 *
 * - Build the answer bit-by-bit from MSB (bit 30) to LSB (bit 0).
 * - For each bit:
 *      - Assume we want this bit in the final AND.
 *      - Check if we can "force" at least `m` numbers to support it
 *        using ≤ k increment operations.
 * - If possible, lock this bit permanently.
 *
 *
 * Time Complexity:
 * ----------------
 * O(31 * n log n)
 *
 * - 31 bits (0 to 30)
 * - For each bit, compute costs for all `n` numbers
 * - Sort costs to get the smallest `m`
 *
 *
 * Space Complexity:
 * -----------------
 * O(n) for cost array
 *
 * -----------------------------------------------------------------------------
 */

class Solution {

    /**
     * Main function to compute maximum possible AND value.
     *
     * @param nums Input array
     * @param k    Maximum number of increment operations allowed
     * @param m    Size of subset
     * @return     Maximum possible bitwise AND
     */
    public int maximumAND(int[] nums, int k, int m) {

        /*
         * `r` stores the AND value constructed so far.
         * It is built incrementally bit-by-bit.
         *
         * Type is `long` to safely handle intermediate bit operations.
         */
        long r = 0;

        /*
         * `c[i]` stores the cost (number of increments)
         * needed to make nums[i] compatible with the current AND target.
         */
        long[] c = new long[nums.length];

        /*
         * Iterate over bits from most significant (30)
         * to least significant (0).
         *
         * Greedy decision:
         * Try to set the current bit if it is affordable.
         */
        for (int bit = 30; bit >= 0; bit--) {

            /*
             * Tentative AND value:
             * - Keep all bits already accepted in `r`
             * - Try adding the current bit
             */
            long t = r | (1L << bit);

            int idx = 0; // number of valid costs filled

            /*
             * Compute the cost for EACH number in nums
             * to make it compatible with target AND `t`.
             */
            for (int x : nums) {

                /*
                 * Default cost = 0.
                 * This remains 0 if `x` already has all bits of `t`.
                 */
                long cost = 0;

                /*
                 * Check if x already satisfies t:
                 * (x & t) == t means all required bits are present.
                 */
                if ((x & t) != t) {

                    /*
                     * `d` stores the bits that are required by `t`
                     * but missing in `x`.
                     */
                    long d = t & ~x;

                    /*
                     * Find the highest missing bit.
                     * This bit dictates where binary carry must happen
                     * during increment operations.
                     */
                    int h = 63 - Long.numberOfLeadingZeros(d);

                    /*
                     * Create a mask with lower bits [0..h] set to 1.
                     * These bits will be RESET due to carry.
                     *
                     * Example:
                     * h = 3 → mask = 000...01111
                     */
                    long mask = (1L << (h + 1)) - 1;

                    /*
                     * Compute the SMALLEST number >= x that:
                     * 1) Clears all lower unreliable bits (due to carry)
                     * 2) Forces all required bits from `t`
                     *
                     * This simulates the first reachable number
                     * that satisfies the AND constraint.
                     */
                    long n = (x & ~mask) | t;

                    /*
                     * Cost is the number of increments required
                     * to go from x to n.
                     */
                    cost = n - x;
                }

                /*
                 * Store cost for this number.
                 */
                c[idx++] = cost;
            }

            /*
             * Sort only the computed costs.
             * We only need the smallest `m` costs.
             */
            Arrays.sort(c, 0, idx);

            /*
             * Sum the smallest `m` costs.
             * These represent the cheapest `m` numbers
             * that can support the target AND `t`.
             */
            long sum = 0;
            for (int i = 0; i < m; i++) {
                sum += c[i];
            }

            /*
             * If total cost is within allowed operations `k`,
             * we permanently accept this bit.
             */
            if (sum <= k) {
                r = t;
            }
        }

        /*
         * Final AND value fits within int range,
         * so cast and return.
         */
        return (int) r;
    }
}
