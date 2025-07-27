import java.util.ArrayList;
import java.util.List;

/**
 ** 8. String to Integer (atoi)
 ** Link: https://leetcode.com/problems/string-to-integer-atoi/
 **
 **
 ** String Problem
 ** Main Emphasis on preventing Integer Overflow
 **
 ** Time Complexity: O(n)
 ** Space Complexity: O(1)
 **
 **
 ** In checking of overflow make conditions according to these num = num * 10 + digit; so condition
 ** would be num > Integer.MAX_VALUE / 10
 ** Remember to reduce value instead of increasing them do this num > Integer.MAX_VALUE / 10 instead (num*10) > Integer.MAX_VALUE
 **
 **
 ** Special Edge Cases:
 ** 1. "-91283472332"
 ** 2. "21474836460"
 ** 3. "2147483648"
 ** 4. "-2147483648"
 **
 **
 ** Important : How this code is handling this edge case "-2147483648" as we are making number
 ** using positive digits and allowed positive number is 2147483647
 ** When num is reached to 214748364 then in case step 8 will be added so num will be 2147483648
 ** So num will be overflown and will become "-2147483648"
 */

public class Main {
    public static void main(String[] args) {
    }
}
class Solution {
    public int myAtoi(String s) {
        int index = 0, num = 0, length = s.length();
        boolean isNegative = false;

        // Skip leading whitespaces
        while (index < length && s.charAt(index) == ' ') {
            index++;
        }

        // Handle optional '+' or '-'
        if (index < length && (s.charAt(index) == '+' || s.charAt(index) == '-')) {
            isNegative = (s.charAt(index) == '-');
            index++;
        }

        // Parse digits
        while (index < length && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';

            // Overflow check before appending digit
            if (num > Integer.MAX_VALUE / 10 ||
                    (num == Integer.MAX_VALUE / 10 && digit > (isNegative ? 8 : 7))) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            num = num * 10 + digit;
            index++;
        }

        return isNegative ? -num : num;
    }
}