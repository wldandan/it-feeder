package com.it.epolice.domain;

import org.apache.commons.net.util.Base64;
import org.junit.Before;
import org.junit.Test;

import java.security.Timestamp;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ImageTypeTest {

    ImageType imageType;
    @Before
    public void setUp() throws Exception {
        imageType  = ImageType.BAYONET;
    }

    @Test
    public void testImageTypeValueOf() throws Exception {
        assertThat(ImageType.BAYONET, is(imageType.valueOf("BAYONET")));
    }

    @Test
    public void testGenerateImageId() throws Exception {
//        System.out.println(Base64.encodeBase64String("机号019车道A12014年03月13日08时44分01秒R004D0H3T标准车牌C蓝底白字P赣D33301驶向南至北违章闯红灯时速17公里坐标(X1425Y127).JPG".getBytes("UTF-8")));
//        System.out.println(UUID.randomUUID().toString());
        System.out.println(new Date().getTime());
        System.out.println(new Date().getTime());
        System.out.println(new Date().getTime());
        System.out.println(new Date().getTime());
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());

//        System.out.println(UUID.fromString("机号019车道A12014年03月13日08时44分01秒R004D0H3T标准车牌C蓝底白字P赣D33301驶向南至北违章闯红灯时速17公里坐标(X1425Y127).JPG"));
//
//        System.out.println(UUID.fromString("机号019车道A12014年03月13日08时45分53秒R036D0H3T标准车牌C蓝底白字P赣D27509驶向南至北违章闯红灯时速16公里坐标(X1574Y99)_S.JPG"));
//        System.out.println(UUID.fromString("机号019车道A12014年03月13日08时45分53秒R036D0H3T标准车牌C蓝底白字P赣D27509驶向南至北违章闯红灯时速16公里坐标(X1574Y99).JPG"));

    }
}
