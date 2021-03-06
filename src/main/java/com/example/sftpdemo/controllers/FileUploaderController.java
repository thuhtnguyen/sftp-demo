package com.example.sftpdemo.controllers;

import com.example.sftpdemo.services.FileUploaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileUploaderController {
    private static final String REMOTE_HOST = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final int PORT = 22;

    @Autowired
    private FileUploaderService fileUploaderService;

    @GetMapping(path = "/upload")
    public String upload() {
        try {
            fileUploaderService.uploadFile(REMOTE_HOST, PORT, USERNAME, PASSWORD, "", "","");
        } catch (Exception e) {
            log.error("Error while uploading file to SFTP", e);
            return "Internal server error";
        }
        return "File uploaded";
    }
}
