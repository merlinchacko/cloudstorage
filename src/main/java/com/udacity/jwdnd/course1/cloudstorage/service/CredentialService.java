package com.udacity.jwdnd.course1.cloudstorage.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class CredentialService
{
    private CredentialMapper credentialMapper;
    private UserMapper userMapper;
    private EncryptionService encryptionService;

    public void createCredential(final Credential credential, final String userName)
    {
        String encodedKey = encodedKey();
        User user = userMapper.getUser(userName);

        credential.setUserId(user.getUserId());
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword(credential.getPassword(), encodedKey));

        credentialMapper.insert(credential);
    }

    public void updateCredential(final Credential credential, final String userName)
    {
        String encodedKey = encodedKey();
        User user = userMapper.getUser(userName);

        credential.setUserId(user.getUserId());
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword(credential.getPassword(), encodedKey));

        credentialMapper.update(credential);
    }

    public void deleteCredential(final Integer credentialId, final String userName)
    {
        User user = userMapper.getUser(userName);
        credentialMapper.delete(credentialId, user.getUserId());
    }

    public List<Credential> getAllCredentials(final String userName)
    {
        User user = userMapper.getUser(userName);
        List<Credential> credentials = credentialMapper.getAllCredentials(user.getUserId());

        for (Credential credential : credentials)
        {
            credential.setDecryptedPassword(decryptedPassword(credential.getPassword(), credential.getKey()));
        }

        return credentials;
    }

    private String encodedKey()
    {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    private String encryptedPassword(String data, String key)
    {
        return encryptionService.encryptValue(data, key);
    }

    private String decryptedPassword(String data, String key)
    {
        return encryptionService.decryptValue(data, key);
    }
}
