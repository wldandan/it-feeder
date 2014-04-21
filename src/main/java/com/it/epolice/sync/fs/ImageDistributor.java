package com.it.epolice.sync.fs;

import com.it.epolice.domain.Image;
import com.it.epolice.domain.ImageStatus;
import com.it.epolice.sync.ImageHandler;
import com.it.epolice.utils.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ImageDistributor implements Distributor, ImageHandler {

    private final String pwd;
    private final String host;
    private final String user;
    private final FTPClient ftpClient;
    private final String distributionPath;

    private static String encoding = System.getProperty("file.encoding");

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ImageDistributor.class);

    public ImageDistributor(String host, String user, String pwd, FTPClient ftpClient, String distributionPath) throws Exception {
        this.host = host;
        this.user =user;
        this.pwd = pwd;
        this.ftpClient = ftpClient;
        this.distributionPath = distributionPath;

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
                LOGGER.info("File [{}] has been distributed to [{}] Successfully", remoteFilePath, localFilePath);
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
            LOGGER.error("Can not login in server with User {}",user);
            throw new IOException("Can not login in server with User " + user);
        }
        LOGGER.info("Login ftp server [{}] with [{}] successfully", host, user);
    }

    @Override
    public Boolean distribute(Image image) throws Exception {
        initDistributePath(image);

        LOGGER.info("started distribute file from [{}] to [{}]", image.getPath(), image.getDistributedPath());
        connect();
        downloadFile(image.getPath(), image.getDistributedPath());
        LOGGER.info("finished distribute file from [{}] to [{}]", image.getPath(), image.getDistributedPath());
        image.setDistributedPath(ImageUtils.generateDistributedPath(image));

        return true;
    }

    private void initDistributePath(Image image) throws IOException {
        FileUtils.forceMkdir(new File(this.distributionPath));
        image.setDistributedPath(distributionPath + "/" + image.getImageId() + "." + image.getImageExt());
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
        LOGGER.info("connect to fpt server [{}]",host);
        this.connect();
    }

    @Override
    public void stop(){
        LOGGER.info("disconnect from ftp server [{}]", host);
//        disconnect();
    }

    @Override
    public Boolean handle(Image image) throws Exception {
        return this.distribute(image);
    }

    @Override
    public int getSuccessCode() {
        return ImageStatus.DISTRIBUTED.getCode();
    }
}