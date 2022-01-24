package com.example.sftpdemo.services;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@Slf4j
public class FileUploaderService {
    private static final String KNOWN_HOSTS_PATH = "";
    private static final String KNOWN_HOSTS_TEXT = "";
    private static final String REMOTE_HOST = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String SSH_PRIVATE_KEY_FILE = "";
    private static final int SESSION_TIMEOUT = 100000;
    private static final int CHANNEL_TIMEOUT = 100000;

    private ChannelSftp setupJschPasswordAuthentication(String host, int port, String username, String password, String knownHost)
            throws JSchException {
        JSch jSch = new JSch();
//        jSch.setKnownHosts(KNOWN_HOSTS_PATH);
        jSch.setKnownHosts(new ByteArrayInputStream(knownHost.getBytes()));
        Session jschSession = jSch.getSession(username, host);
        jschSession.setPort(port);
        jschSession.setPassword(password);
        jschSession.setConfig("StrictHostKeyChecking", "yes");
        jschSession.connect(SESSION_TIMEOUT);
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    private ChannelSftp setupJschSSHAuthentication() throws JSchException {
        JSch jSch = new JSch();
//        jSch.setKnownHosts(KNOWN_HOSTS_PATH);
        jSch.setKnownHosts(new ByteArrayInputStream(KNOWN_HOSTS_TEXT.getBytes()));
        Session jschSession = jSch.getSession(USERNAME, REMOTE_HOST);
        jschSession.setPort(2222);
        jSch.addIdentity(SSH_PRIVATE_KEY_FILE);

        jschSession.connect(SESSION_TIMEOUT);
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public boolean uploadFile(String host, int port, String username, String password, String fileToUpload,
                           String destinationFile, String knownHost) {
        try {
            ChannelSftp channelSftp = setupJschPasswordAuthentication(host, port, username, password, knownHost); // Using username/password
//        ChannelSftp channelSftp = setupJschSSHAuthentication(); // Using SSH key
            channelSftp.connect(CHANNEL_TIMEOUT);

            channelSftp.put(fileToUpload, destinationFile);
            channelSftp.exit();
            return true;
        } catch (Exception e) {
            log.error("Error while uploading file to sftp server", e);
            return false;
        }
    }
}