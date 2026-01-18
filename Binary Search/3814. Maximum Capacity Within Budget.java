import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * 3814. Maximum Capacity Within Budget
 * Link : https://leetcode.com/problems/maximum-capacity-within-budget/description/
 * -----------------------------------------------------------------------------
 *
 * Problem Summary:
 * ----------------
 * You are given:
 *  - costs[i]    → cost of item i
 *  - capacity[i] → capacity of item i
 *  - budget      → maximum allowed total cost
 *
 * You can choose:
 *  - At most TWO items
 *
 * Goal:
 * -----
 * Maximize the TOTAL CAPACITY such that:
 *
 *      total_cost < budget
 *
 *
 * Key Observations:
 * -----------------
 * 1) Choosing ONE item:
 *    - Simply take the maximum capacity among items
 *      whose cost < budget.
 *
 * 2) Choosing TWO items:
 *    - Let first item be fixed.
 *    - Second item must have:
 *          cost < (budget - first_cost)
 *
 * 3) Brute force (O(n²)) is too slow.
 *
 *
 * Core Strategy:
 * --------------
 * SORT + PREFIX MAX + BINARY SEARCH
 *
 * Step-by-step plan:
 * ------------------
 * 1) Combine (cost, capacity) into a single array.
 * 2) Sort items by cost.
 * 3) Build a prefix array:
 *      prefix[i] = maximum capacity among items [0 .. i]
 * 4) For each item i:
 *      - Consider it as the FIRST item
 *      - Binary search for the BEST second item
 *        whose cost < (budget - cost[i])
 *      - Use prefix array to get max capacity efficiently
 *
 *
 * Time Complexity:
 * ----------------
 * O(n log n)
 *
 * - Sorting: O(n log n)
 * - Loop with binary search: O(n log n)
 *
 *
 * Space Complexity:
 * -----------------
 * O(n)
 *
 * - Combined array + prefix array
 *
 * -----------------------------------------------------------------------------
 */

class Solution {

    /**
     * Returns the maximum achievable capacity within the given budget.
     *
     * @param costs     Cost array
     * @param capacity  Capacity array
     * @param budget    Maximum allowed cost
     * @return          Maximum total capacity
     */
    public int maxCapacity(int[] costs, int[] capacity, int budget) {

        int n = costs.length;

        /*
         * Combine cost and capacity into a single structure:
         * arr[i][0] = cost
         * arr[i][1] = capacity
         */
        int[][] arr = new int[n][2];

        /*
         * prefix[i] stores the MAX capacity
         * among items from index 0 to i (after sorting).
         */
        int[] prefix = new int[n];

        /*
         * Build combined array.
         */
        for (int i = 0; i < n; i++) {
            arr[i][0] = costs[i];
            arr[i][1] = capacity[i];
        }

        /*
         * Sort items by cost (ascending).
         * This enables binary search on cost.
         */
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        /*
         * Build prefix maximum capacity array.
         *
         * prefix[i] = max capacity seen so far up to i
         */
        int bestSoFar = 0;
        for (int i = 0; i < n; i++) {
            bestSoFar = Math.max(bestSoFar, arr[i][1]);
            prefix[i] = bestSoFar;
        }

        /*
         * This variable stores the final answer.
         */
        int maxCapacity = 0;

        /*
         * Try each item as the FIRST selected item.
         */
        for (int i = 0; i < n; i++) {

            int cost1 = arr[i][0];
            int cap1  = arr[i][1];

            /*
             * If even one item exceeds or equals the budget,
             * no further items can be chosen (array is sorted).
             */
            if (cost1 >= budget) break;

            /*
             * Case 1: Take only ONE item.
             */
            maxCapacity = Math.max(maxCapacity, cap1);

            /*
             * Remaining budget after choosing first item.
             */
            int remainingBudget = budget - cost1;

            /*
             * Binary search in range [0 .. i-1]
             * to find the largest index such that:
             *
             *      arr[idx][0] < remainingBudget
             */
            int low = 0, high = i - 1;
            int idx = -1;

            while (low <= high) {
                int mid = low + (high - low) / 2;

                if (arr[mid][0] < remainingBudget) {
                    idx = mid;        // valid candidate
                    low = mid + 1;    // try to find a better one
                } else {
                    high = mid - 1;
                }
            }

            /*
             * If a valid second item exists,
             * use prefix array to get maximum capacity.
             */
            if (idx != -1) {
                maxCapacity = Math.max(
                        maxCapacity,
                        cap1 + prefix[idx]
                );
            }
        }

        /*
         * Return the maximum capacity achievable.
         */
        return maxCapacity;
    }
}
