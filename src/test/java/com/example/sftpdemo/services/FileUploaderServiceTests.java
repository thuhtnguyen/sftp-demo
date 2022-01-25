package com.example.sftpdemo.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class FileUploaderServiceTests {
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

    @Value("${MAX_RETRIES}")
    private int maxRetries;

    @Autowired
    private FileUploaderService fileUploaderService;

    @Test
    public void uploadFile() {
        boolean result = false;
        try {
//            result = fileUploaderService.uploadFile(sftpHost, sftpPort, sftpUsername, sftpPassword, fileToUpload,
//                            sftpUploadPath + destFileName, knownHost);
            result = fileUploaderService.uploadFileWithRetry(sftpHost, sftpPort, sftpUsername, sftpPassword, fileToUpload,
                            sftpUploadPath + destFileName, knownHost);
        } catch (Exception e) {
            log.error("Error while uploading file to SFTP on all of {} attempt(s)", maxRetries, e);
        }
        Assertions.assertTrue(result);
    }
}
