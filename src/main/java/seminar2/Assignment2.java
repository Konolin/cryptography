package seminar2;

import java.util.Scanner;

public class Assignment2 {
    private static final int[] VALID_A = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
    private static final double[] ENGLISH_FREQUENCIES = {
            8.17,  // A
            1.49,  // B
            2.78,  // C
            4.25,  // D
            12.70, // E
            2.23,  // F
            2.02,  // G
            6.09,  // H
            6.97,  // I
            0.15,  // J
            0.77,  // K
            4.03,  // L
            2.41,  // M
            6.75,  // N
            7.51,  // O
            1.93,  // P
            0.10,  // Q
            5.99,  // R
            6.33,  // S
            9.06,  // T
            2.76,  // U
            0.98,  // V
            2.36,  // W
            0.15,  // X
            1.97,  // Y
            0.07   // Z
    };

    // QOZFZFNCHNSFHRZCPUFTNBXZGGBFHQNSZCWQOHYGPZCQHIQ
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ciphertext:");

        String cipherText = scanner.nextLine().trim().toUpperCase();
        scanner.close();

        Result result = hackAffine(cipherText);
        System.out.println("Decrypted text: " + result.plainText);
        System.out.println("Key used: a = " + result.a + ", b = " + result.b);
    }

    private static Result hackAffine(String cipherText) {
        double bestScore = Double.NEGATIVE_INFINITY;
        String bestPlainText = "";
        int bestA = 0, bestB = 0;

        for (int a : VALID_A) {
            int a_inv = modInverse(a, 26);

            for (int b = 0; b < 26; b++) {
                String candidate = decrypt(cipherText, a_inv, b);
                double score = scoreCandidate(candidate);
                if (score > bestScore) {
                    bestScore = score;
                    bestPlainText = candidate;
                    bestA = a;
                    bestB = b;
                }
            }
        }
        return new Result(bestA, bestB, bestPlainText);
    }

    private static String decrypt(String cipherText, int a_inv, int b) {
        StringBuilder sb = new StringBuilder();
        for (char c : cipherText.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                int y = c - 'A';
                int x = (a_inv * (y - b + 26)) % 26;
                char plainChar = (char) (x + 'A');
                sb.append(plainChar);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1)
                return x;
        }
        return -1;
    }

    private static double scoreCandidate(String text) {
        double score = 0.0;
        score -= monogramDifferences(text);

        // count bigrams
        String[] commonBigrams = {"TH", "HE", "IN", "ER", "AN", "RE", "ND", "AT", "ON", "NT"};
        for (String bigram : commonBigrams) {
            int count = countOccurrences(text, bigram);
            score += count;
        }

        // count trigrams
        String[] commonTrigrams = {"THE", "AND", "ING", "ENT", "ION"};
        for (String trigram : commonTrigrams) {
            int count = countOccurrences(text, trigram);
            score += count;
        }
        return score;
    }

    private static double monogramDifferences(String text) {
        int[] counts = new int[26];
        int total = 0;
        // count occurrences of each letter
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                counts[c - 'A']++;
                total++;
            }
        }
        double score = 0.0;
        for (int i = 0; i < 26; i++) {
            // expected frequency of each letter in English text
            // (based on the percentage of each letter in the English language)
            // add the difference between the expected and actual frequency => lower score means its a bad candidate
            double expected = total * (ENGLISH_FREQUENCIES[i] / 100.0);
            if (expected > 0) {
                score += counts[i] - expected;
            }
        }
        return score;
    }

    private static int countOccurrences(String text, String sub) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(sub, index)) != -1) {
            count++;
            index++;
        }
        return count;
    }

    private static class Result {
        public final int a;
        public final int b;
        public final String plainText;

        public Result(int a, int b, String plainText) {
            this.a = a;
            this.b = b;
            this.plainText = plainText;
        }
    }
}
