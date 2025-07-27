import java.util.ArrayList;
import java.util.List;

/**
 ** 216. Combination Sum III
 ** Link: https://leetcode.com/problems/combination-sum-iii/
 **
 **
 ** Recursion Backtracking Problem
 **
 **
 ** Time Complexity: O(C(9,k)*k) k is no of elements to be taken,C is Combinatorial
 ** C(9,k) gives all ways of taking k elements form 1 to 9 numbers
 ** k is the time for making the new ArrayList for storing to ans -> ans.add(new ArrayList<>(ds));
 **
 **
 ** Space Complexity: O(C(9, k) * k) (output) + O(k) (Recursion stack)
 ** In the worst case, all possible combinations are valid, and each has n elements in a list
 */

class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> combinationSum3(int k, int n) {
        generateCombinations(k,n,0,new ArrayList<>(),0,1);

        return ans;
    }

    private void generateCombinations(int k,int sum,int index,List<Integer> ds,int dsSum,int start){
        if(k==index && dsSum==sum){
            ans.add(new ArrayList<>(ds));
            return;
        }

        if(k==index && dsSum != sum) return;
        if(dsSum>sum) return;//As there are no negative numbers, so dsSum cannot be decreased so its best to early exit

        for(int i=start;i<10;i++){
            ds.add(i);
            generateCombinations(k,sum,index+1,ds,dsSum+i,i+1);
            ds.remove(ds.size()-1);
        }
    }
}