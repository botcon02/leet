// https://leetcode.com/problems/decode-ways/

class DecodeWays {
    // Idea 1: Dynamic programming. Maintain 2 types of dp.
    // 1) Number of ways where the last element is end of a 2 digit group. ie. "26" <- 26 = z
    // 2) Number of ways where the last element is not end of a 2 digit group. ie. "26" <- bf
    // Number of ways to decode = (1) + (2).
    // endOf[k] = notEndOf(k - 1) (only when certain criteria are met, otherwise 0).
    // notEndOf[k] = endOf[k - 1] + notEndOf[k - 1].
    public int numDecodings(String s) {
        // Dummy values to facilitate when s.length = 0.
        int endOf = 0;
        int notEndOf = 1;
        char prevChar = '3';
        
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int temp1 = endOf;
            int temp2 = notEndOf;
            char currChar = s.charAt(i);
            
            // In the case where currChar = 0, it must be part of a sequence.
            if (currChar == '0') {
                // No possible ways to decode.
                if (prevChar >= '3') {
                    return 0;
                }
                endOf = temp2;
                notEndOf = 0;
            } else {
                if (prevChar == '1' || prevChar == '2' && currChar <= '6') {
                    endOf = temp2;
                } else {
                    endOf = 0;
                }
                // In either cases, currChar can be treated as a number by itself (unlike 0).
                notEndOf = temp1 + temp2;
            }
            
            prevChar = currChar;
            
        }
        return endOf + notEndOf;
    }
}
