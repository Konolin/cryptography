package seminar2;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Assignment1 {
    private static String alphabet;

    // WRDQVZHUWKHILQDOH0DP1RXQHHGWRXVHVHPLQDUVWRILQGFLSKHUWH0W
    public static void main(String[] args) {
        String input = readInput().toUpperCase();
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + extractAndSortNonLetters(input);
        hackShift(input);
    }

    private static String readInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the ciphertext: ");
            return scanner.nextLine();
        }
    }


    private static Pair<Integer, String> hackShift(final String cipherText) {
        for (int shift = 0; shift < alphabet.length(); shift++) {
            String candidate = decode(cipherText, shift);
            System.out.println("Shift " + shift + " => " + candidate);
        }
        return new Pair<>(0, "");
    }

    private static String decode(String cipherText, int shift) {
        StringBuilder sb = new StringBuilder();
        int n = alphabet.length();

        for (char c : cipherText.toCharArray()) {
            int idx = alphabet.indexOf(c);
            if (idx != -1) {
                int newIdx = (idx - shift + n) % n;
                sb.append(alphabet.charAt(newIdx));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static String extractAndSortNonLetters(String input) {
        return input.chars()
                .filter(c -> !Character.isLetter(c))
                .sorted()
                .mapToObj(c -> String.valueOf((char)c))
                .collect(Collectors.joining());
    }

    static class Pair<K, V> {
        final K key;
        final V ciphertext;

        public Pair(K key, V ciphertext) {
            this.key = key;
            this.ciphertext = ciphertext;
        }
    }
}
