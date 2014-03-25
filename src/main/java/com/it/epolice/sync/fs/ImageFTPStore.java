package com.it.epolice.sync.fs;

import com.it.epolice.domain.Image;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ImageFTPStore implements ImageStore {

    private final String pwd;
    private final String host;
    private final String user;
    private final FTPClient ftpClient;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ImageFTPStore.class);
 
    public ImageFTPStore(String host, String user, String pwd, FTPClient ftpClient) throws Exception {
        this.host = host;
        this.user =user;
        this.pwd = pwd;
        this.ftpClient = ftpClient;

    }

    protected void connect() throws Exception {

        ftpClient.setControlKeepAliveTimeout(300);

        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftpClient.connect(host);
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftpClient.login(user, pwd);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
    }

    public void downloadFile(String remoteFilePath, String localFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(localFilePath);
        try{
            if (ftpClient.retrieveFile(remoteFilePath, fos)){
                System.out.println("File " + remoteFilePath + " has been downloaded successfully!");
            }
        }
        finally {
            fos.close();
        }
    }
 
    public void disconnect() {
        if (this.ftpClient.isConnected()) {
            try {
                this.ftpClient.logout();
                this.ftpClient.disconnect();
            } catch (IOException f) {
            }
        }
    }
 
    @Override
    public Boolean generate(Image image) throws Exception {
//        downloadFile(image.getOriginalPath(), image.getDistributedPath());
        LOGGER.info("downloading file " + image.getOriginalPath() + " to " + image.getDistributedPath());
        return true;
    }

    @Override
    public void start(){
        LOGGER.info("connect to server");
//        connect();
    }

    @Override
    public void stop(){
        LOGGER.info("disconnect ftp server");
//        disconnect();
    }
}