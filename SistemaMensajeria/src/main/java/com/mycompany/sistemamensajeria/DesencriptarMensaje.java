/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemamensajeria;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Marcos T.
 */
public class DesencriptarMensaje {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "B/lUwQC\"`nI8Ze>+eI~qLSWEwbE`?Zt~";

    public static String desencriptar(String encryptedData) throws Exception {
        byte[] decodedEncryptedData = Base64.getDecoder().decode(encryptedData);

        byte[] iv = new byte[16];
        System.arraycopy(decodedEncryptedData, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        int encryptedSize = decodedEncryptedData.length - iv.length;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(decodedEncryptedData, iv.length, encryptedBytes, 0, encryptedSize);

        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(encryptedBytes);

        return new String(decrypted);
    }
}
