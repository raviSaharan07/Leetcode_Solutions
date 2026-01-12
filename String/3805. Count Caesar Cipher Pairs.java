import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * 3805. Count Caesar Cipher Pairs
 * Link : https://leetcode.com/problems/count-caesar-cipher-pairs/description/
 * -----------------------------------------------------------------------------
 *
 * Problem Summary:
 * ----------------
 * You are given an array of strings `words`.
 *
 * Two strings are considered a VALID PAIR if:
 *  - One string can be converted into the other by **uniformly shifting**
 *    all characters by the same number of positions in the alphabet.
 *
 * Alphabet rules:
 *  - Characters wrap around cyclically
 *    ('z' + 1 → 'a')
 *
 * Goal:
 * -----
 * Count the number of pairs (i, j) such that:
 *  - i < j
 *  - words[i] and words[j] are "shift-equivalent"
 *
 *
 * Key Observations:
 * -----------------
 * 1) Absolute characters DO NOT matter.
 *    Only the RELATIVE difference between characters matters.
 *
 *    Example:
 *      "abc" → differences: [0,1,2]
 *      "bcd" → differences: [0,1,2]
 *      "xyz" → differences: [0,1,2]
 *
 *    All three are equivalent under shifting.
 *
 * 2) If we normalize every word into a canonical form,
 *    then equivalent words become IDENTICAL strings.
 *
 * 3) Once normalized, the problem reduces to:
 *    "Count pairs of equal strings"
 *
 *
 * Core Strategy:
 * --------------
 * HASHING + NORMALIZATION
 *
 * - Convert each word into a normalized representation:
 *      • Treat the first character as base
 *      • Shift every character so that first character becomes 'a'
 *      • Store relative distances modulo 26
 *
 * - Use a HashMap to count how many times each normalized word appears.
 *
 * - While processing:
 *      • If a normalized word has appeared `x` times before,
 *        then it forms `x` new valid pairs with the current word.
 *
 *
 * Example Walkthrough:
 * --------------------
 * words = ["abc", "bcd", "xyz", "az", "ba"]
 *
 * Normalized forms:
 *   "abc" → "abc"
 *   "bcd" → "abc"
 *   "xyz" → "abc"
 *   "az"  → "az"
 *   "ba"  → "az"
 *
 * Pairs:
 *   "abc" group → C(3,2) = 3
 *   "az" group  → C(2,2) = 1
 *   Total = 4
 *
 *
 * Time Complexity:
 * ----------------
 * O(N * L)
 *
 * - N = number of words
 * - L = average length of a word
 * - Each character is processed once
 *
 *
 * Space Complexity:
 * -----------------
 * O(N * L) in worst case for storing normalized strings
 *
 * -----------------------------------------------------------------------------
 */

class Solution {

    /**
     * Counts the number of valid pairs of shift-equivalent strings.
     *
     * @param words Array of input strings
     * @return      Number of valid pairs
     */
    public long countPairs(String[] words) {

        /*
         * HashMap to store:
         *   key   → normalized representation of a word
         *   value → how many times it has appeared so far
         */
        Map<String, Integer> freq = new HashMap<>();

        /*
         * Final answer:
         * Use `long` because number of pairs can exceed int range.
         */
        long pairs = 0;

        /*
         * Process each word independently.
         */
        for (String word : words) {

            /*
             * Convert word to character array
             * so we can modify characters in-place.
             */
            char[] chars = word.toCharArray();

            /*
             * Choose the FIRST character as the base.
             * We will shift the entire word so that
             * this base character becomes 'a'.
             */
            char base = chars[0];

            /*
             * Normalize the word:
             * ---------------------------------------------
             * For each character:
             *   - Compute relative distance from base
             *   - Apply modulo 26 to handle wrap-around
             *
             * Formula:
             *   (current - base + 26) % 26
             *
             * Example:
             *   word = "bcd"
             *   base = 'b'
             *
             *   'b' → (b - b + 26) % 26 = 0 → 'a'
             *   'c' → (c - b + 26) % 26 = 1 → 'b'
             *   'd' → (d - b + 26) % 26 = 2 → 'c'
             *
             *   normalized = "abc"
             */
            for (int i = 0; i < chars.length; i++) {
                chars[i] = (char) ((chars[i] - base + 26) % 26);
            }

            /*
             * Convert normalized char array back to String.
             * This acts as the canonical representation.
             */
            String normalizedWord = new String(chars);

            /*
             * Get how many times this normalized word
             * has appeared before.
             */
            int count = freq.getOrDefault(normalizedWord, 0);

            /*
             * If it has appeared `count` times,
             * then current word forms `count` new pairs.
             */
            pairs += count;

            /*
             * Update frequency for future words.
             */
            freq.put(normalizedWord, count + 1);
        }

        /*
         * Return total number of valid pairs.
         */
        return pairs;
    }
}
