package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.LimitExceededException;
import javax.naming.SizeLimitExceededException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;
    AuthenticationService authenticationService;

    public FileService(FileMapper fileMapper, AuthenticationService authenticationService) {
        this.fileMapper = fileMapper;
        this.authenticationService = authenticationService;
    }

    public int uploadFile(MultipartFile uploadedFile) throws IOException {

        Long size = uploadedFile.getSize();
        if(size <= 0)
        {
            throw new FileNotFoundException("File not found");
        }
        int userid = authenticationService.getCurrentUserId();
        String filename = uploadedFile.getOriginalFilename();
        if(fileMapper.isFileAlreadyExists(filename, userid))
        {
            throw new FileAlreadyExistsException("File already Exists");
        }
        String contentType = uploadedFile.getContentType();
        byte[] data = uploadedFile.getBytes();
        return fileMapper.insert(new File(null, filename, contentType, String.valueOf(size), userid, data));
    }

    public List<File> getAllFiles()
    {
        return fileMapper.getAllFiles(authenticationService.getCurrentUserId());
    }

    public int deleteFile(int fileId)
    {
        return fileMapper.delete(fileId);
    }

    public File getFile(Integer fileId)
    {
        File file = fileMapper.getFile(fileId);
        if(file.getUserid() == authenticationService.getCurrentUserId())
        {
            return file;
        }
        return null;
    }

}
