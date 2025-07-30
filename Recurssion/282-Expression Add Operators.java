import java.util.ArrayList;
import java.util.List;

/**
 ** 282. Expression Add Operators
 ** Link: https://leetcode.com/problems/expression-add-operators/
 **
 **
 ** Recursion Backtracking Problem
 ** Hard
 **
 ** Time Complexity: O(4^(n-1))
 ** We are exploring every possibility
 ** n-1 would be the spaces between n digits and for every space we have possibility 4 to fill
 ** that is +, -, * or nothing (to make a multi-digit number)
 **
 **
 ** Space Complexity: O(n) (Recursive Stack and StringBuilder Length) + O(4^(n - 1) * n) (Result Output)
 ** Recursive Stack would be n length deep and the maximum size of StringBuilder can be n(digits) + n-1(operators)
 ** Since one stringBuilder is shared among all it will not be multiplied, rather added
 ** For output total number of possibles would be 4^(n-1) and for the worst case each string can be of length
 ** 2n-1 so 4^(n-1)*(2n-1)
 **
 ** In this problem, the most important to learn is how to evaluate a result with the correct order of
 ** precedence. Multiplication has higher precedence than Addition and Subtraction,
 ** So for handling multiplication precedence, we have initialized variable lastOperand which contains the last operand
 ** How this works:
 ** -> In case of Addition:
 ** Suppose the expression is 2+3*5
 ** When we come to 5, the expression will become 5*5. What we will do is remove 3 from the
 ** coming result and multiply 3 with 5 and add to this
 ** 5*5 -> (5-3)+(3*5) == 2 + (3*5)
 ** Here 5 will be our eval and 5 will be number at index and 3 will be lastOperand.
 **
 ** -> In case of Subtraction:
 ** Suppose the expression is 2-3*5
 ** When we come to 5, the expression will become -1*5. What we will do now is add 3 the last operand
 ** from result and multiply 3 with 5 and subtract to the result
 ** -1*5 -> (-1+3)-(3*5) == 2-(3*5)
 ** That's why we have returned -currentNum in "-" case for lastOperand so that sign can be adjusted as above
 **
 ** -> In case of Multiplication:
 ** In this case we have returned lastOperand*currentNum as lastOperand as they need to be treated as one unit
 ** due to higher precedence of multiplication
 ** Consider this expression : 3+2*5*2
 ** What we're doing is making expression as this: 3+20
 ** 3+2*5*2 -> 5*5*2 -> (5-2)+(2*5)*2 -> 13*2 At this stage lastOperand will be 10 not 5
 ** 3+10*2->13*2 -> (13-10)+(2*10) -> 23
 */

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.addOperators("105",5));
    }
}
class Solution {
    List<String> ans = new ArrayList<>();

    public List<String> addOperators(String num, int target) {
        backtrack(num.toCharArray(), target, 0, 0, 0, new StringBuilder());
        return ans;
    }

    private void backtrack(char[] chars, int target, int index, long eval, long lastOperand, StringBuilder path) {
        if (index == chars.length) {
            if (eval == target) {
                ans.add(path.toString());
            }
            return;
        }

        long currentNum = 0;
        //For restoring the string builder to previous state in case of backtrack
        int len = path.length();

        for (int i = index; i < chars.length; i++) {
            // Discarding the leading zeros
            if (i != index && chars[index] == '0') break;
            currentNum = currentNum * 10 + (chars[i] - '0');

            if (index == 0) {
                // Just take the first index
                path.append(currentNum);
                backtrack(chars, target, i + 1, currentNum, currentNum, path);
                path.setLength(len);
            } else {
                path.append('+').append(currentNum);
                backtrack(chars, target, i + 1, eval + currentNum, currentNum, path);
                path.setLength(len);

                path.append('-').append(currentNum);
                backtrack(chars, target, i + 1, eval - currentNum, -currentNum, path);
                path.setLength(len);

                path.append('*').append(currentNum);
                backtrack(chars, target, i + 1, eval - lastOperand + lastOperand * currentNum, lastOperand * currentNum, path);
                path.setLength(len);
            }
        }
    }
}