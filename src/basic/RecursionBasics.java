package basic;

public class RecursionBasics {
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static int zaehleSelbstlaute(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        char firstChar = Character.toLowerCase(s.charAt(0));
        int count = (firstChar == 'a' || firstChar == 'e' || firstChar == 'i' || firstChar == 'o' || firstChar == 'u') ? 1 : 0;
        return count + zaehleSelbstlaute(s.substring(1));
    }

    public static int coinSumsRecursive(int amount, int[] coins, int index) {
        if (amount == 0) {
            return 1;
        }
        if (amount < 0 || index < 0) {
            return 0;
        }
        return coinSumsRecursive(amount - coins[index], coins, index)
                + coinSumsRecursive(amount, coins, index - 1);
    }

    public static int coinSums(int amount) {
        int[] coins = {1, 2, 5, 10, 20, 50, 100, 200};
        return coinSumsRecursive(amount, coins, coins.length - 1);
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println("Fibonacci of " + n + " is " + fibonacci(n));
        String s = "Hallo Welt";
        System.out.println("Anzahl der Selbstlaute in \"" + s + "\" ist " + zaehleSelbstlaute(s));
        System.out.println("coinSums(200) = " + coinSums(200));
    }


}
