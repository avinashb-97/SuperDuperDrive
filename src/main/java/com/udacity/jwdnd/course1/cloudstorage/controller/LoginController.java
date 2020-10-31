package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    AuthenticationService authenticationService;

    LoginController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }


    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(name = "error", required = false) String error,
                               @RequestParam(name = "logout", required = false) String logout)
    {
        if(authenticationService.isAuthenticated())
        {
            return "redirect:home";
        }
        if(error != null)
        {
            model.addAttribute("message","invalidUserNameOrPassword");
        }
        if(logout != null)
        {
            model.addAttribute("message","logout");
        }
        return "login";
    }

}
