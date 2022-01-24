package com.example.sftpdemo.services;

import com.example.sftpdemo.utils.RetryHelper;
import lombok.extern.slf4j.Slf4j;
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
        RetryHelper<Object> retryHelper = new RetryHelper<>(maxRetries);
        retryHelper.run(() -> fileUploaderService.uploadFile(sftpHost, sftpPort, sftpUsername, sftpPassword, fileToUpload,
                sftpUploadPath + destFileName, knownHost));
    }
}
