import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

/**
 * CS3230 - Design and analysis of algorithms
 * - Problem -
 * Given 2n distinct integers, a1,a2,a3,...,an and b1,b2,b3,...,bn from 1 to 2n
 * Required to insert elements of b into the array a, let c be the resulting array of size 2n after insertion
 * Note that the relative order of array a must be preserved, but not the order of array b
 * Among all possible insertion sequence, what is the minimum number of inversions
 * (inversion is a pair (i, j) where i < j and ci > cj)
 *
 * - Input -
 * Input consist of 3 lines. First line consist of a single integer n.
 * Second line consist of n space separated a1,a2,a3,...,an.
 * Third line consist of n space separated b1,b2,b3,...,bn.
 *
 * - Output -
 * Single integer, the minimum possible number of inversion c can have.
 */
public class MinimumInversions {
    // Standard reader class provided for user input.
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
        General solution: Create the array with least inversion and find value.
        Step 1: Sort b
        Step 2: Find index of array a where each b should be inserted into for least inversion
        Step 3: Create array of least inversion by merging a and b based on Step 2
        Step 4: Find inversion of the array with standard merge sort.

        Sample input
        4
        5 4 1 2
        8 3 6 7
        Sample output
        7
    */

    // Counts the number of inversion within the array by using merge sort.
    private static long countInversion(int[] arr, int l, int r) {
        if (l < r) {
            int mid = l + (r - l) / 2;
            long leftInversion = countInversion(arr, l, mid);
            long rightInversion = countInversion(arr, mid + 1, r);
            long currInversion = merge(arr, l, mid, r);
            return leftInversion + rightInversion + currInversion;
        }
        return 0;
    }

    // Merges 2 sub-arrays, returning the number of inversions.
    private static long merge(int[] arr, int l, int mid, int r) {
        int[] output = new int[r - l + 1];
        int lptr = l;
        int rptr = mid + 1;
        int k = 0;
        long inversion = 0;
        while (lptr <= mid && rptr <= r) {
            if (arr[rptr] < arr[lptr]) {
                inversion += mid - lptr + 1;
                output[k] = arr[rptr];
                rptr++;
            } else {
                output[k] = arr[lptr];
                lptr++;
            }
            k++;
        }
        // Remaining element of first half
        while (lptr <= mid) {
            output[k] = arr[lptr];
            lptr++;
            k++;
        }
        // Remaining element of second half
        while (rptr <= r) {
            output[k] = arr[rptr];
            rptr++;
            k++;
        }
        // Copy output into arr[l...r]
        for (int i = 0; i < output.length; i++) {
            arr[l + i] = output[i];
        }

        return inversion;
    }

    // Creates the array b` where b`1,b`2,...b`n and b`i is the optimal index for bi to be inserted into a.
    private static void intermediate(int[] interArray, int[] a, int[] b, int aLeft, int aRight, int bLeft, int bRight) {
        if (bLeft > bRight) {
            return;
        }
        int bMid = bLeft + (bRight - bLeft) / 2;
        int aMid = findIndex(b[bMid], aLeft, aRight, a);
        interArray[bMid] = aMid;
        intermediate(interArray, a, b, aLeft, aMid - 1, bLeft, bMid - 1);
        intermediate(interArray, a, b, aMid, aRight, bMid + 1, bRight);
    }

    // Given a integer, find index with the least inversion
    private static int findIndex(int target, int left, int right, int[] arr) {
        if (left > right) {
            return left;
        }
        int index = left;
        int minInversion = 0;
        // find all values lesser than target
        for (int i = left; i <= right; i++) {
            if (arr[i] < target) {
                minInversion++;
            }
        }

        int tempInversion = minInversion;
        for (int i = left; i <= right; i++) {
            if (arr[i] < target) {
                tempInversion--;
            } else {
                tempInversion++;
            }
            if (tempInversion < minInversion) {
                index = i + 1;
                minInversion = tempInversion;
            }
        }

        return index;
    }

    // Solver
    public static long solve(int[] a, int[] b) {

        // Create the array with minimum inversion
        Arrays.sort(b); // Sort b in ascending order
        int len = a.length;
        int[] intermediate = new int[a.length];
        intermediate(intermediate, a, b, 0, len - 1, 0, len - 1);
        int[] output = new int[len * 2];
        int j = 0, k = 0;
        for (int i = 0; i < intermediate.length; i++) {
            int pos = intermediate[i];
            while (j < pos) {
                output[k] = a[j];
                j++;
                k++;
            }
            output[k] = b[i];
            k++;
        }

        while (j < len) {
            output[k] = a[j];
            j++;
            k++;
        }
        // Above code creates array with minimum inversion

        return countInversion(output, 0 , output.length - 1);
        }

    // Driver function
    public static void main(String[] args) throws IOException {

        Reader s = new Reader();
        int n = s.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for(int i=0; i<n; i++) {
            a[i] = s.nextInt();
        }
        for(int i=0; i<n; i++) {
            b[i] = s.nextInt();
        }
        System.out.println(solve(a, b));
    }
}