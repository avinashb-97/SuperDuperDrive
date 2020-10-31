package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constants.CredentialConstants;
import com.udacity.jwdnd.course1.cloudstorage.constants.FileConstants;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialsController {

    CredentialService credentialService;
    EncryptionService encryptionService;

    public CredentialsController(CredentialService credentialService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/credentials")
    public String addCredentials(Credential credential, RedirectAttributes redirectAttributes)
    {
        if(credential.getId() == null)
        {
            credentialService.addCredential(credential);
            redirectAttributes.addFlashAttribute("actionMessage", CredentialConstants.ADD_SUCCESS);
        }
        else
        {
            credentialService.updateCredential(credential);
            redirectAttributes.addFlashAttribute("actionMessage", CredentialConstants.EDIT_SUCCESS);
        }
        redirectAttributes.addFlashAttribute("currentTab","credentials");
        redirectAttributes.addFlashAttribute("actionSuccess","true");
        return "redirect:home";
    }

    @GetMapping("/deleteCredential")
    public String deleteNote(@RequestParam(name = "credentialId", required = false) Integer credentialId, RedirectAttributes redirectAttributes)
    {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("currentTab","credentials");
        redirectAttributes.addFlashAttribute("actionSuccess","true");
        redirectAttributes.addFlashAttribute("actionMessage", CredentialConstants.DELETE_SUCCESS);
        return "redirect:home";
    }
}
