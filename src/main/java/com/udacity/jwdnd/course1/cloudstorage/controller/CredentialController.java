package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;


@Controller
@AllArgsConstructor
public class CredentialController
{
    private CredentialService credentialService;

    @SneakyThrows
    @PostMapping(value = "/addCredential")
    public String addCredential(Authentication authentication, Credential credential, Model model)
    {
        if (credential.getCredentialId() != null)
        {
            credentialService.updateCredential(credential, authentication.getName());
            model.addAttribute("message", "Credential updated successfully.");
        }
        else
        {
            credentialService.createCredential(credential, authentication.getName());
            model.addAttribute("message", "Credential created successfully.");
        }
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping("/deleteCredential/{id}")
    public String deleteCredential(Authentication authentication, @PathVariable("id") Integer credentialId, Model model)
    {
        credentialService.deleteCredential(credentialId, authentication.getName());
        model.addAttribute("message", "Credential deleted successfully.");
        model.addAttribute("result", "success");
        return "result";
    }
}
