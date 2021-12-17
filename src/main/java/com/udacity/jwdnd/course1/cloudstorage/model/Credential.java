package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class Credential
{
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;
    private String decryptedPassword;

    public Credential(String url, String username, String key, String password)
    {
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
    }

}
