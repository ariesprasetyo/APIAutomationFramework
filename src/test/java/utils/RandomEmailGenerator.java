package utils;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RandomEmailGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int EMAIL_LENGTH = 10;



    public static String generateRandomEmail() {
        StringBuilder emailBuilder = new StringBuilder();
        Random random = new Random();

        // Membuat bagian acak dari email
        for (int i = 0; i < EMAIL_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            emailBuilder.append(CHARACTERS.charAt(index));
        }

        // Menambahkan domain Gmail
        emailBuilder.append("@gmail.com");

        return emailBuilder.toString();
    }
}
