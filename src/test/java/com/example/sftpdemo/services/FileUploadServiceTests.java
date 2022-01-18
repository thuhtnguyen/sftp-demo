package com.example.sftpdemo.services;

import com.example.sftpdemo.services.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class FileUploadServiceTests {
    @Value("${SFTP_HOST}")
    private String sftpHost;

    @Value("${SFTP_USERNAME}")
    private String sftpUsername;

    @Value("${SFTP_PASSWORD}")
    private String sftpPassword;

    @Value("${SFTP_PORT}")
    private int sftpPort;

    @Value("${SFTP_UPLOAD_PATH}")
    private String sftpUploadPath;

    @Value("${DEST_FILE_NAME}")
    private String destFileName;

    @Value("${FILE_TO_UPLOAD}")
    private String fileToUpload;

    @Value("${KNOWN_HOST}")
    private String knownHost;

    @Autowired
    private FileUploadService fileUploadService;

    @Test
    public void uploadFile() {
        try {
            fileUploadService.uploadFile(sftpHost, sftpPort, sftpUsername, sftpPassword, fileToUpload,
                    sftpUploadPath + destFileName, knownHost);
        } catch (Exception e) {
            log.error("Error while uploading file to sftp server", e);
        }
    }
}
