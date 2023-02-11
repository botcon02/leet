import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.*;

/**
 * CS3230 - Design and Analysis of Algorithms
 * - Problem -
 * You are given an array of n integers a1, a2, . . . , an. An interval [i, j] (i < j) is balanced iff, among
 * ai, ai+1, . . . , aj , every integer appears an even (possibly zero) number of times.
 * For example, if a = [3, 3, 1, 2, 2, 1], the balanced intervals are [1, 2], [3, 6], [4, 5], [1, 6] corresponding
 * to the contiguous subarrays [3, 3], [1, 2, 2, 1], [2, 2], [3, 3, 1, 2, 2, 1].
 * If a = [1, 2, 3, 2, 1], then there are no balanced intervals.
 * - Input -
 * Input consists of two lines. The first line contains a single integer n, denoting the size of the array a.
 * The second line contains n integers a1, a2, . . . , an.
 * - Output -
 * Output a single integer, the number of balanced intervals, followed by a newline character.
 *
 */
public class Hashing {
    static Random rand = new Random();
    private static int random1 = rand.nextInt();
    private static int random2 = rand.nextInt();

    // fast input library taken from
    // https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
    /*
        General Solution: Use hashing.
        Use XOR operator to find matches
        Try to map each integer uniformly to {0,1,...2^64 -1} to reduce false positive.
        Exists low probability of failure
     */

    // Some random hash function to map to non-negative.
    private static long hash(long target) {
        long temp = random1 * target;
        temp = (temp + 23) * random2;
        return temp;
    }

    public static long solve(int[] a) {
        // write your code here
        long total = 0;
        long hash = 0;
        HashMap<Long, Integer> counter = new HashMap<>();
        // The empty hash = 0 is initialized to 1.
        counter.put(hash, 1);
        int min = Arrays.stream(a).min().getAsInt();

        for (int value : a) {
            // XOR the hash value.
            hash ^= hash(value - min + 1);
            if (counter.containsKey(hash)) {
                int curr = counter.get(hash);
                total += curr;
                counter.replace(hash, curr + 1);
            } else {
                counter.put(hash, 1);
            }
        }

        return total;
    }

    public static void main(String[] args) throws IOException {
        Reader s = new Reader();
        int n = s.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for(int i=0; i<n; i++) {
            a[i] = s.nextInt();
        }
        System.out.println(solve(a));
    }
}