package com.example.eservice.models.service;

public interface IEncryptDecryptService {

    public String encryptKey(String plainKey);
     
    public String decryptKey(String encryptedKey);

}