package seminar1;

import java.util.Scanner;

public class Assignment1 {
    public static void main(String[] args) {
        Pair<Integer, String> userInput = readInput();
        int key = userInput.key();
        String ciphertext = userInput.value();
        String decrypted = decryptText(key, ciphertext);

        System.out.println("The decrypted text is: " + decrypted);
    }

    private static String decryptText(final int key, final String ciphertext) {
        StringBuilder decryptedText = new StringBuilder();

        for (char encryptedChar : ciphertext.toCharArray()) {
            decryptedText.append(shiftCharLeft(encryptedChar, key));
        }

        return decryptedText.toString();
    }

    private static Pair<Integer, String> readInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the ciphertext: ");
            String ciphertext = scanner.nextLine();

            System.out.println("Enter the key: ");
            int key = scanner.nextInt();

            return new Pair<>(key, ciphertext);
        }
    }

    private static char shiftCharLeft(final char c, final int shift) {
        if (Character.isLowerCase(c)) {
            return (char) ('a' + (c - 'a' - shift + 26) % 26);
        } else if (Character.isUpperCase(c)) {
            return (char) ('A' + (c - 'A' - shift + 26) % 26);
        }
        return c;
    }
    
    static class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }
}

}

