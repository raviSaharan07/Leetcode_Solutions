import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * 1461. Check If a String Contains All Binary Codes of Size K
 * Link : https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k/
 * -----------------------------------------------------------------------------
 *
 * Problem Summary:
 * ----------------
 * You are given:
 *  - A binary string `s`
 *  - An integer `k`
 *
 * Goal:
 * -----
 * Return TRUE if every possible binary code of length `k`
 * appears as a substring of `s`.
 *
 *
 * What does this mean?
 * --------------------
 * For k = 3 → total possible binary codes = 2^3 = 8
 *
 * They are:
 *   000, 001, 010, 011, 100, 101, 110, 111
 *
 * We must check whether ALL of them appear in `s`.
 *
 *
 * -----------------------------------------------------------------------------
 * 🔑 KEY MATHEMATICAL INSIGHT
 * -----------------------------------------------------------------------------
 *
 * Total possible binary strings of length k:
 *
 *      2^k
 *
 * We must see ALL of them at least once.
 *
 * -----------------------------------------------------------------------------
 */

/*
 * Visualizing total number of required binary codes:
 */

::contentReference[oaicite:0]{index=0}


/**
 * -----------------------------------------------------------------------------
 * CORE IDEA:
 * -----------------------------------------------------------------------------
 *
 * Instead of storing substrings (slow),
 * we use a ROLLING HASH (bitmask technique).
 *
 *
 * Observations:
 * -------------
 * 1) We can represent every k-length binary substring
 *    as an integer between 0 and (2^k - 1).
 *
 * 2) Use a sliding window of size k.
 *
 * 3) Maintain a rolling hash:
 *
 *      hash = ((hash << 1) & mask) | currentBit
 *
 *    - Shift left → make room for new bit
 *    - Apply mask → keep only last k bits
 *    - OR with current bit
 *
 *
 * 4) Track seen hashes using a boolean array of size 2^k.
 *
 *
 * Optimization Trick:
 * -------------------
 * - Maintain a counter `remaining`
 * - Initially = 2^k
 * - Each time we see a new pattern → decrement
 * - If remaining becomes 0 → return true immediately
 *
 *
 * Time Complexity:
 * ----------------
 * O(n)
 *
 * - Each character processed once
 *
 *
 * Space Complexity:
 * -----------------
 * O(2^k)
 *
 * - Boolean array storing visited patterns
 *
 * -----------------------------------------------------------------------------
 */

class Solution {

    /**
     * Checks whether string `s` contains all possible
     * binary substrings of length `k`.
     *
     * @param s Binary string
     * @param k Length of binary codes
     * @return  true if all codes exist, otherwise false
     */
    public boolean hasAllCodes(String s, int k) {

        /*
         * Total required distinct patterns = 2^k
         */
        int remaining = 1 << k;

        /*
         * Mask keeps only the last k bits.
         *
         * Example:
         * k = 3 → mask = 0b111 = 7
         */
        int mask = remaining - 1;

        /*
         * visited[i] = whether pattern `i` has appeared.
         */
        boolean[] visited = new boolean[remaining];

        /*
         * Rolling hash value representing
         * current k-length window.
         */
        int hash = 0;

        /*
         * Traverse the string.
         */
        for (int i = 0; i < s.length(); i++) {

            /*
             * Step 1:
             * Shift left by 1 (multiply by 2).
             *
             * Step 2:
             * Apply mask to remove bits older than k.
             *
             * Step 3:
             * Add current bit.
             *
             * (s.charAt(i) & 1)
             * works because:
             *   '0' & 1 = 0
             *   '1' & 1 = 1
             */
            hash = ((hash << 1) & mask) | (s.charAt(i) & 1);

            /*
             * Only start checking once we have
             * at least k characters processed.
             */
            if (i >= k - 1) {

                /*
                 * If this pattern is seen for first time:
                 */
                if (!visited[hash]) {

                    visited[hash] = true;
                    remaining--;

                    /*
                     * Early exit:
                     * If all patterns found.
                     */
                    if (remaining == 0) {
                        return true;
                    }
                }
            }
        }

        /*
         * If some patterns were never found.
         */
        return false;
    }
}