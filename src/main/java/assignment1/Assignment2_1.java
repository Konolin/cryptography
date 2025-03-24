package assignment1;

import java.util.Scanner;

public class Assignment2_1 {
    public static void main(String[] args) {
        Trio<Integer, Integer, String> userInput = readInput();
        try {
            String encryptedText = encryption(userInput.a(), userInput.b(), userInput.text());
            System.out.println("Encrypted text: " + encryptedText);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Trio<Integer, Integer, String> readInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the plaintext: ");
            String plaintext = scanner.nextLine();

            System.out.println("Enter a: ");
            int a = scanner.nextInt();

            System.out.println("Enter b: ");
            int b = scanner.nextInt();

            return new Trio<>(a, b, plaintext);
        }
    }

    private static String encryption(int a, int b, String plaintext) {
        validateGCDRule(a);
        StringBuilder encryptedText = new StringBuilder();
        for (char plainChar : plaintext.toCharArray()) {
            encryptedText.append(encryptChar(a, b, plainChar));
        }
        return encryptedText.toString();
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

    private static char encryptChar(int a, int b, char c) {
        // e(x) = a * x + b
        // c - 'a' is used to remove the offset of the alphabet in the ASCII table (ex. 'a' is 97)
        // % 26 used to loop around the alphabet (ex. for x + 1 -> a)
        // 'a' + result adds the offset again
        if (Character.isLowerCase(c)) {
            return (char) ('a' + ((c - 'a') * a + b) % 26);
        }
        if (Character.isUpperCase(c)) {
            return (char) ('A' + ((c - 'A') * a + b) % 26);
        }
        return c;
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