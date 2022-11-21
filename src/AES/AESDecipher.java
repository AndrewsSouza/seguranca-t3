package AES;/*
   Programa para decifrar uma mensagem passada como argumento.
   Usa o AES sem o modo de operação.
 
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

//  import javax.xml.bind.DatatypeConverter;

public class AESDecipher {
    // Função para converter uma String em hexadecimal para
    // um array de bytes
    private static byte[] toByteArray(String s) {
        return StringUtils.hexStringToByteArray(s);
    }

    public static String decode(String chaveHexadecimal, String textoCifradoHexadecimal) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(toByteArray(chaveHexadecimal), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = toByteArray(textoCifradoHexadecimal.substring(0, 32));
        byte[] cypherText = toByteArray(textoCifradoHexadecimal.substring(32));

        IvParameterSpec ivp = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivp);

        byte[] deciphered = cipher.doFinal(cypherText);

        return new String(deciphered);
    }
}
