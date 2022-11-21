package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static byte[] hashSha256(byte[] src) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Falha ao gerar o hash");
            e.printStackTrace();
        }

        assert messageDigest != null;
        messageDigest.update(src);

        return messageDigest.digest();

    }
}
