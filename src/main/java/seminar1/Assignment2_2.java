package seminar1;

import java.util.Scanner;

public class Assignment2_2 {
    public static void main(String[] args) {
        Trio<Integer, Integer, String> userInput = readInput();
        try {
            String decryptedText = decryption(userInput.a(), userInput.b(), userInput.text());
            System.out.println("Decrypted text: " + decryptedText);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Trio<Integer, Integer, String> readInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the ciphertext: ");
            String plaintext = scanner.nextLine();

            System.out.println("Enter a: ");
            int a = scanner.nextInt();

            System.out.println("Enter b: ");
            int b = scanner.nextInt();

            return new Trio<>(a, b, plaintext);
        }
    }

    private static String decryption(int a, int b, String ciphertext) {
        validateGCDRule(a);
        int aInv = multiplicativeInverse(a, 26);
        StringBuilder decryptedText = new StringBuilder();
        for (char encryptedChar: ciphertext.toCharArray()) {
            decryptedText.append(decryptChar(aInv, b, encryptedChar));
        }
        return decryptedText.toString();
    }

    private static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    private static void validateGCDRule(int a) {
        int gcd = gcd(a, 26);
        if (gcd != 1) {
            throw new RuntimeException("Error: a and 26 must have a gcd = 1, not " + gcd);
        }
    }

    private static char decryptChar(int aInv, int b, char c) {
        // d(y) = aInv(y - b)
        // c - 'a' is used to remove the offset of the alphabet in the ASCII table (ex. 'a' is 97)
        // % 26 used to loop around the alphabet (ex. for x + 1 -> a)
        // 'a' + result adds the offset again
        if (Character.isLowerCase(c)) {
            return (char) ('a' + Math.floorMod(aInv * (c - 'a' - b), 26));
        }
        if (Character.isUpperCase(c)) {
            return (char) ('A' + Math.floorMod(aInv * (c - 'A' - b), 26));
        }
        return c;
    }


    private static int multiplicativeInverse(int a, int m) {
        for (int aInv = 1; aInv < m; aInv++) {
            if ((a * aInv - 1) % m == 0) {
                return aInv;
            }
        }
        throw new ArithmeticException("No multiplicative inverse for " + a + " mod " + m);
    }

    static class Trio<K, S, V> {
        private final K a;
        private final S b;
        private final V text;

        public Trio(K a, S b, V text) {
            this.a = a;
            this.b = b;
            this.text = text;
        }

        public K a() {
            return a;
        }

        public S b() {
            return b;
        }

        public V text() {
            return text;
        }
    }
}