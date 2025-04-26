package basic;

import java.util.HashMap;
import java.util.Map;

public class LongestCollatzSequence {

    static Map<Long, Integer> cache = new HashMap<>();

    public static int collatz(long n) {
        if (n == 1) return 1;
        if (cache.containsKey(n)) return cache.get(n);

        long next = (n % 2 == 0) ? n / 2 : 3 * n + 1;
        int result = 1 + collatz(next);
        cache.put(n, result);
        return result;
    }


    public static void main(String[] args) {
        int maxLength = 0;
        int startingNumber = 0;
        for (int i = 1; i < 1_000_000; i++) {
            int length = collatz(i);
            if (length > maxLength) {
                maxLength = length;
                startingNumber = i;
            }
        }
        System.out.println("The starting number under one million that produces the longest Collatz sequence is: " + startingNumber);
        System.out.println("The length of the sequence is: " + maxLength);
    }
}