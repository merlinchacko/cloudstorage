package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController
{
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model)
    {
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getName()));

        return "home";
    }
}
