package assignment1;

import java.util.Scanner;

public class Assignment3 {
    public static void main(String[] args) {
        Pair userInput = getUserInput();
        int[] key = userInput.key();

        try {
            String encryptedText = encryption(key[0], key[1], key[2], key[3], userInput.text());
            System.out.println("Encrypted text: " + encryptedText);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Pair getUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter plain text");
            String plainText = scanner.nextLine();

            // for [11 8] do like this: 11 'enter', 8 'enter', 12 'enter', 9 'enter'
            //      12 9
            System.out.println("Enter key (one int at a time): ");
            int[] userInput = new int[4];
            userInput[0] = scanner.nextInt();
            userInput[1] = scanner.nextInt();
            userInput[2] = scanner.nextInt();
            userInput[3] = scanner.nextInt();

            return new Pair(userInput, plainText);
        }
    }

    private static String encryption(int c11, int c12, int c21, int c22, String plaintext) {
        if (gcd(det(c11, c12, c21, c22), 26) != 1) {
            throw new RuntimeException("Matrix does not have inverse modulo 26");
        }

        // make sure the length of the plaintext is even
        if (plaintext.length() % 2 == 1) {
            plaintext += 'x';
        }

        StringBuilder encryptedText = new StringBuilder();
        int index = 0;
        while (index < plaintext.length()) {
            // get the offsets
            char offset1 = Character.isLowerCase(plaintext.charAt(index)) ? 'a' : 'A';
            char offset2 = Character.isLowerCase(plaintext.charAt(index + 1)) ? 'a' : 'A';
            
            // get the characters
            char c1 = (char) (plaintext.charAt(index) - offset1);
            char c2 = (char) (plaintext.charAt(index + 1 ) - offset2);

            // encrypt them
            char enC1 = (char) (offset1 + (c1 * c11 + c2 * c21) % 26);
            char enC2 = (char) (offset2 + (c1 * c12 + c2 * c22) % 26);

            encryptedText.append(enC1).append(enC2);
            index += 2;
        }

        return encryptedText.toString();
    }

    private static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    private static int det(int c11, int c12, int c21, int c22) {
        return c11 * c22 - c12 * c21;
    }


    static class Pair {
        private final int[] key;
        private final String text;

        public Pair(int[] key, String text) {
            this.key = key;
            this.text = text;
        }

        public int[] key() {
            return key;
        }

        public String text() {
            return text;
        }
    }
}