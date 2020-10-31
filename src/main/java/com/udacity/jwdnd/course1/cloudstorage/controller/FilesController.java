package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constants.FileConstants;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.SizeLimitExceededException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Controller
@ControllerAdvice
public class FilesController {

    FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/files")
    public String addFile(@RequestParam(name = "file", required=false) MultipartFile file, RedirectAttributes redirectAttributes)
    {
            try
            {
                fileService.uploadFile(file);
                redirectAttributes.addFlashAttribute("actionSuccess","true");
                redirectAttributes.addFlashAttribute("actionMessage",FileConstants.UPLOAD_SUCCESS);
            }
            catch (IOException e)
            {
                if(e.getClass().equals(FileNotFoundException.class))
                {
                    redirectAttributes.addFlashAttribute("actionWarning","true");
                    redirectAttributes.addFlashAttribute("actionMessage",FileConstants.FILE_NOT_FOUND);
                }
                else if(e.getClass().equals(FileAlreadyExistsException.class))
                {
                    redirectAttributes.addFlashAttribute("actionError","true");
                    redirectAttributes.addFlashAttribute("actionMessage",FileConstants.FILE_ALREADY_EXISTS);
                }
                else
                {
                    redirectAttributes.addFlashAttribute("actionError","true");
                    redirectAttributes.addFlashAttribute("actionMessage",FileConstants.IO_EXCEPTION);
                }
            }
//        }
        redirectAttributes.addFlashAttribute("currentTab","files");
        return "redirect:home";
    }

    @GetMapping(value = "/deleteFile")
    public String deleteFile(@RequestParam(name = "fileId", required=false) Integer fileId, RedirectAttributes redirectAttributes)
    {
        fileService.deleteFile(fileId);
        redirectAttributes.addFlashAttribute("currentTab","files");
        redirectAttributes.addFlashAttribute("actionSuccess","true");
        redirectAttributes.addFlashAttribute("actionMessage",FileConstants.DELETE_SUCCESS);
        return "redirect:home";
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") String id)
    {
        File file = fileService.getFile(Integer.parseInt(id));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename= "+file.getFilename())
                .body(new ByteArrayResource(file.getData()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("currentTab","files");
        redirectAttributes.addFlashAttribute("actionError","true");
        redirectAttributes.addFlashAttribute("actionMessage",FileConstants.LIMIT_EXCEEDED);
        return "redirect:home";
    }
}
