package AES;/*
 Programa para cifrar uma mensagem passada como argumento.
 Usa o AES sem modo de operação.
 
 Não existe consistência no programa.

 Autor: Avelino F. Zorzo
 Data: 10.09.2018
 
 */

import utils.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESCipher {

    // Funcão para converter um array de bytes para uma
    // String em hexadecimal

    public static String toHexString(byte[] array) {
        return StringUtils.byteArrayToHexString(array);
    }

    // Função para converter uma String em hexadecimal para
    // um array de bytes
    public static byte[] toByteArray(String s) {
        return StringUtils.hexStringToByteArray(s);
    }

    public static byte[] generateRandomIv() {
        SecureRandom randomSecureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        randomSecureRandom.nextBytes(iv);
        return iv;
    }

    public static String encode(String chaveHexadecimal, String mensagemHexadecimal) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skSpec = new SecretKeySpec(toByteArray(chaveHexadecimal), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = generateRandomIv();
        IvParameterSpec ivp = new IvParameterSpec(iv);

        byte[] text = StringUtils.hexStringToByteArray(mensagemHexadecimal);
        cipher.init(Cipher.ENCRYPT_MODE, skSpec, ivp);

        byte[] encrypted = cipher.doFinal(text);

        return  toHexString(iv) + toHexString(encrypted);
    }
}
