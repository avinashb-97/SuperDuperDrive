package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    AuthenticationService authenticationService;
    CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(AuthenticationService authenticationService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.authenticationService = authenticationService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials()
    {
        int userid = authenticationService.getCurrentUserId();
        List<Credential> credentials = credentialMapper.getCredentials(userid);
        for(Credential credential : credentials)
        {
            String decryptValue = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setDecryptedPassword(decryptValue);
        }
        return credentials;
    }

    public int addCredential(Credential credential)
    {
        int userid = authenticationService.getCurrentUserId();
        credential.setUserid(userid);
        String key = getEncryptionKey();
        String password = encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(password);
        credential.setKey(key);
        return credentialMapper.insert(credential);
    }

    private String getEncryptionKey()
    {
        SecureRandom random = new SecureRandom();
        byte[] keyByte = new byte[16];
        random.nextBytes(keyByte);
        return Base64.getEncoder().encodeToString(keyByte);
    }

    public int updateCredential(Credential credential)
    {
        String key = getEncryptionKey();
        String password = encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(password);
        credential.setKey(key);
        return credentialMapper.update(credential);
    }

    public int deleteCredential(int credentialId)
    {
        return credentialMapper.delete(credentialId);
    }
}
