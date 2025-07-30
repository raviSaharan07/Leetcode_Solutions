import java.util.ArrayList;
import java.util.List;

/**
 * Lazily evaluated implementation using AbstractList:
 *
 * This returns a custom list whose elements
 * are only computed when accessed through `.get()` or `.size()`.
 *
 * The DFS logic is wrapped inside an AbstractList subclass, and the `build()` method
 * runs only when needed — this avoids unnecessary computation if the result is never used.
 *
 * This pattern is useful for optimizing performance/memory in large or unused result sets where all result elements might not be used only few are needed.
 */

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.addOperators("105",5));
    }
}
class Solution {
    public List<String> addOperators(String num, int target) {
        return new java.util.AbstractList<>() {
            List<String> res = null;

            private void build() {
                if (res != null) return;
                res = new ArrayList<>();
                dfs(0, num, target, 0, 0, "");
            }

            private void dfs(int idx, String num, int tar, long val, long prev, String path) {
                // dfs logic here
            }

            @Override
            public String get(int i) {
                build();
                return res.get(i);
            }

            @Override
            public int size() {
                build();
                return res.size();
            }
        };
    }
}
