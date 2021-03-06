package com.udacity.jwdnd.course1.cloudstorage.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EncryptionService
{

    public String encryptValue(String data, String key)
    {
        byte[] encryptedValue = null;

        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedValue = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
            | IllegalBlockSizeException | BadPaddingException e)
        {
            log.error(e.getMessage());
        }

        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String decryptValue(String data, String key)
    {
        byte[] decryptedValue = null;

        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedValue = cipher.doFinal(Base64.getDecoder().decode(data));
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException
            | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            log.error(e.getMessage());
        }

        return new String(decryptedValue);
    }
}
