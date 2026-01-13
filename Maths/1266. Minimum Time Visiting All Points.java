import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * 1266. Minimum Time Visiting All Points
 * Link : https://leetcode.com/problems/minimum-time-visiting-all-points/description/?envType=daily-question&envId=2026-01-12
 * -----------------------------------------------------------------------------
 *
 * Problem Summary:
 * ----------------
 * You are given `n` points on a 2D plane, where:
 *
 *   points[i] = [xi, yi]
 *
 * You must visit ALL points in the **given order**.
 *
 * Allowed moves (each taking exactly 1 second):
 *  - Move 1 unit vertically
 *  - Move 1 unit horizontally
 *  - Move 1 unit vertically + 1 unit horizontally (diagonal move)
 *
 * Important:
 * ----------
 * - You may pass through future points, but they do NOT count as visited.
 * - Only reaching the exact point in order matters.
 *
 *
 * Goal:
 * -----
 * Return the MINIMUM time (in seconds) required to visit all points.
 *
 *
 * Key Observations:
 * -----------------
 * 1) Diagonal movement is EXTREMELY powerful:
 *    - In 1 second, you can reduce BOTH x-distance and y-distance by 1.
 *
 * 2) For two points:
 *      (x1, y1) → (x2, y2)
 *
 *    Let:
 *      dx = |x2 - x1|
 *      dy = |y2 - y1|
 *
 * 3) Optimal strategy:
 *    - First use diagonal moves as much as possible
 *    - Then move straight (horizontal or vertical) for remaining distance
 *
 * 4) Number of diagonal moves = min(dx, dy)
 *    Remaining straight moves = |dx - dy|
 *
 * 5) Total time required:
 *
 *        min(dx, dy) + |dx - dy|
 *      = max(dx, dy)
 *
 *
 * 🔑 FINAL INSIGHT:
 * ----------------
 * Time needed to go from one point to the next is:
 *
 *      max( |x2 - x1| , |y2 - y1| )
 *
 *
 * Core Strategy:
 * --------------
 * GREEDY + MATH
 *
 * - Traverse points in order
 * - For every consecutive pair:
 *      • Compute dx and dy
 *      • Add max(dx, dy) to total time
 *
 *
 * Time Complexity:
 * ----------------
 * O(n)
 *
 * - Single pass through the points array
 *
 *
 * Space Complexity:
 * -----------------
 * O(1)
 *
 * - No extra data structures used
 *
 * -----------------------------------------------------------------------------
 */

class Solution {

    /**
     * Computes the minimum time to visit all points in order.
     *
     * @param points 2D array where points[i] = [xi, yi]
     * @return       Minimum time required
     */
    public int minTimeToVisitAllPoints(int[][] points) {

        /*
         * This variable accumulates the total minimum time.
         */
        int minTime = 0;

        /*
         * Start from the SECOND point and compute
         * time needed to move from previous point.
         */
        for (int i = 1; i < points.length; i++) {

            /*
             * Difference in x-coordinates
             */
            int dx = Math.abs(points[i][0] - points[i - 1][0]);

            /*
             * Difference in y-coordinates
             */
            int dy = Math.abs(points[i][1] - points[i - 1][1]);

            /*
             * As derived:
             * Minimum time = max(dx, dy)
             *
             * Reason:
             * - min(dx, dy) diagonal moves
             * - remaining |dx - dy| straight moves
             */
            minTime += Math.max(dx, dy);
        }

        /*
         * Return the final accumulated time.
         */
        return minTime;
    }
}
