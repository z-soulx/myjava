package com.example.javabase.algorithm.leetcode.editor.cn;

//给定一个二进制字符串 s 和一个正整数 n，如果对于 [1, n] 范围内的每个整数，其二进制表示都是 s 的 子字符串 ，就返回 true，否则返回 fa
//lse 。 
//
// 子字符串 是字符串中连续的字符序列。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "0110", n = 3
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：s = "0110", n = 4
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s[i] 不是 '0' 就是 '1' 
// 1 <= n <= 109 
// 
// Related Topics 字符串 
// 👍 107 👎 0

public class BinaryStringWithSubstringsRepresenting1ToN{
      public static void main(String[] args) {
           Solution solution = new BinaryStringWithSubstringsRepresenting1ToN().new Solution();
      }
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean queryString(String s, int n) {

      for (int i = 1; i <= n; i++) {
        if (!s.contains(Integer.toBinaryString(i))) return false;

      }
      return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

  }