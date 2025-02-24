package basic;

import java.util.HashMap;
import java.util.Map;

public class LongestCollatzSequence {

    private static final Map<Long, Integer> collatzCache = new HashMap<>();

    public static int collatzSequenceLength(long n) {
        if (n == 1) {
            return 1;
        }
        if (collatzCache.containsKey(n)) {
            return collatzCache.get(n);
        }
        int length;
        if (n % 2 == 0) {
            length = 1 + collatzSequenceLength(n / 2);
        } else {
            length = 1 + collatzSequenceLength(3 * n + 1);
        }
        collatzCache.put(n, length);
        return length;
    }

    public static void main(String[] args) {
        int maxLength = 0;
        int startingNumber = 0;
        for (int i = 1; i < 1_000_000; i++) {
            int length = collatzSequenceLength(i);
            if (length > maxLength) {
                maxLength = length;
                startingNumber = i;
            }
        }
        System.out.println("The starting number under one million that produces the longest Collatz sequence is: " + startingNumber);
        System.out.println("The length of the sequence is: " + maxLength);
    }
}