// https://leetcode.com/problems/find-median-from-data-stream/

class MedianFinder {
    
    // Idea 1 : Underlying data structure - List
    // addNum - O(logn) with Collections.binarySearch(), findMedian - O(1).
    // Way too slow. Too much unnecessary order
    
    // Idea 2 : Underlying data structure - Min/MaxHeap 
    // addNum - O(logn), findMedian - O(1).
    
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;

    public MedianFinder() {
        this.minHeap = new PriorityQueue<>();
        this.maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    }
    
    public void addNum(int num) {
        // By default, minHeap will store the extra element.
        if (minHeap.size() - maxHeap.size() == 0) {
            maxHeap.add(num);
            minHeap.add(maxHeap.poll());
        } else {
            minHeap.add(num);
            maxHeap.add(minHeap.poll());
        }
    }
    
    public double findMedian() {
        if (minHeap.size() - maxHeap.size() == 0) {
            return (minHeap.peek() + maxHeap.peek()) / 2.0;
        } else {
            return minHeap.peek();
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
