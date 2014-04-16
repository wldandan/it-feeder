package com.it.epolice.sync.fs;

import com.it.epolice.domain.Image;
import com.it.epolice.utils.ImageUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ImageDistributor implements Distributor {

    private final String pwd;
    private final String host;
    private final String user;
    private final FTPClient ftpClient;

    private static String encoding = System.getProperty("file.encoding");

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ImageDistributor.class);
 
    public ImageDistributor(String host, String user, String pwd, FTPClient ftpClient) throws Exception {
        this.host = host;
        this.user =user;
        this.pwd = pwd;
        this.ftpClient = ftpClient;

    }

    protected void connect() throws Exception {
        if (ftpClient.isConnected()){
            return;
        }
        initFTPConfig();
        initConnection();
        initLogin();

    }

    public void downloadFile(String remoteFilePath, String localFilePath) throws Exception {
        FileOutputStream fos = new FileOutputStream(localFilePath);
        try{
            if (ftpClient.retrieveFile(remoteFilePath, fos)){
                LOGGER.info("File [" + remoteFilePath + "] has been distributed to [" + localFilePath + "] Successfully");
            }
        }
        finally {
            fos.close();
        }
    }
 
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                LOGGER.debug("SERVER: " + aReply);
            }
        }
    }

    private void initFTPConfig() throws IOException {
        ftpClient.setControlKeepAliveTimeout(3000);
        ftpClient.setControlEncoding(encoding);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setAutodetectUTF8(true);

    }

    private void initConnection() throws IOException {
        ftpClient.connect(host);
        showServerReply(ftpClient);

        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftpClient.setKeepAlive(true);

        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            LOGGER.error("Can not connect to server with the host " + this.host);
            throw new IOException("Can not connect to server" + this.host);
        }
        LOGGER.info("Connect to ftp server " + host + "successfully!");
    }

    private void initLogin() throws IOException {
        boolean success = ftpClient.login(user, pwd);
        showServerReply(ftpClient);

        if (!success) {
            LOGGER.error("Can not login in server with User" + user);
            throw new IOException("Can not login in server with User" + user);
        }
        LOGGER.info("Login ftp server " + host + " with " + user + "successfully!");
    }

    @Override
    public Boolean distribute(Image image) throws Exception {
        LOGGER.info("started distribute file from " + image.getPath() + " to " + image.getDistributedPath());
        connect();
        downloadFile(image.getPath(), image.getDistributedPath());
        LOGGER.info("finished distribute file from " + image.getPath() + " to " + image.getDistributedPath());
        image.setDistributedPath(ImageUtils.generateDistributedPath(image));

        return true;
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
    public void start() throws Exception {
        LOGGER.info("connect to fpt server -" + this.host);
        this.connect();
    }

    @Override
    public void stop(){
        LOGGER.info("disconnect from ftp server -" + this.host);
//        disconnect();
    }
}