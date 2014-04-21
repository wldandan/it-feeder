package com.it.epolice.sync.fs;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ImageDistributorTest {

    private ImageDistributor distributor;

    @Before
    public void setUp() throws Exception {
        distributor = new ImageDistributor("localhost", "it", "it", new FTPClient(), "");
        distributor.connect();
    }

    @After
    public void tearDown() throws Exception {
        distributor.disconnect();
    }

    @Test
    public void testDownloadFile() throws Exception {

        distributor.downloadFile(
                "/机号017车道B12014年03月13日01时30分30秒RX09D1H1驶向北至南违章超速时速61公里_S.JPG",
                "/tmp/image/X1425Y127.JPG");

        String directory = "/2014年03月13日卡口/C017图片目录/违章图片目录/";

        List<String> files = newArrayList();
        files.add("机号017车道B12014年03月13日01时30分30秒RX09D1H1驶向北至南违章超速时速61公里_S.JPG");
        files.add("机号017车道B12014年03月13日09时20分31秒RX33D1H1驶向北至南违章超速时速46公里_S.JPG");
        files.add("机号017车道B12014年03月13日09时25分56秒RX18D1H1T标准车牌C蓝底白字P新BW2152驶向北至南违章超速时速41公里坐标(X1301Y315)车体灰黑_S.JPG");
        files.add("机号017车道B12014年03月13日09时45分03秒RX90D1H1T标准车牌C蓝底白字P吉AX2098驶向北至南违章超速时速67公里坐标(X1392Y262)车体深黄_S.JPG");
        files.add("机号017车道B12014年03月13日09时53分49秒RX57D1H1驶向北至南违章超速时速55公里_S.JPG");
        files.add("机号017车道B12014年03月13日09时58分15秒RX41D1H1T标准车牌C蓝底白字P新H98955驶向北至南违章超速时速50公里坐标(X1140Y318)车体灰白_S.JPG");

        for (String file : files) {
            distributor.downloadFile(
                    directory + file, "/tmp/image/" + System.nanoTime() + ".JPG");
        }
    }

}
