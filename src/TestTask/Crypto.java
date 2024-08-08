package TestTask;

public class Crypto {
    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            encrypted.append((char) (c + 1));
        }
        return encrypted.toString();
    }

    public String decrypt(String input) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            decrypted.append((char) (c - 1));
        }
        return decrypted.toString();
    }
}
