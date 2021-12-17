package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/signup")
public class SignupController
{
    private final UserService userService;

    @GetMapping()
    public String signupView()
    {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model)
    {
        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername()))
        {
            signupError = "The username already exists.";
        }

        if (signupError == null)
        {
            int userId = userService.createUser(user);
            if (userId < 0)
            {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null)
        {
            model.addAttribute("signupSuccess", true);
            return "login";
        }
        else
        {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
