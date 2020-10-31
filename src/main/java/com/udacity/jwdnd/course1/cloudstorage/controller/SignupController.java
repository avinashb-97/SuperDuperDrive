package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;
    AuthenticationService authenticationService;

    public SignupController(UserService userService, AuthenticationService authenticationService)
    {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping()
    public String getSignupPage()
    {
        if(authenticationService.isAuthenticated())
        {
            return "redirect:home";
        }
        return "signup";
    }

    @PostMapping
    public String signUp(User user, Model model, RedirectAttributes redirectAttributes)
    {
        String username = user.getUsername();
        boolean isNewUser = !userService.isUsernameAvailable(username);
        if(isNewUser)
        {
            userService.createUser(user);
        }
        else
        {
            model.addAttribute("userAdded",isNewUser);
            return "signUp";
        }
        redirectAttributes.addFlashAttribute("message","signupSuccess");
        return "redirect:login";
    }
}
