package com.it.epolice.sync.parser;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.it.epolice.domain.Image;
import com.it.epolice.domain.ImageType;
import com.it.epolice.domain.ViolationType;
import com.it.epolice.utils.ImageUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(MockitoJUnitRunner.class)
public class ImageParserTest {

    @Test
    public void testParse() throws Exception {
        String json = Resources.toString(Resources.getResource("request-image.json"), Charsets.UTF_8);
        List<Image> images = new ImageParser().parse(json);
        assertThat(images.size() > 0, is(true));

        Image image = images.get(0);

        assertThat(image.getVehicle().getNumber(), is("陕A01L01"));
        assertThat(image.getVehicle().getColor(), is("黑"));
        assertThat(image.getVehicle().getDescription(), is("标准车牌C蓝底白字"));
        assertThat(image.getVehicle().getSpeed(), is(61));
        assertThat(image.getVehicle().getUnit(), is("公里/时"));

        assertThat(image.getSource().getHost(), is("127.0.0.1"));
        assertThat(image.getSource().getPort(), is(21));
        assertThat(image.getSource().getProtocol(), is("ftp"));
        assertThat(image.getSource().getPath(), is("/2014年03月13日卡口/C017图片目录/违章图片目录/机号017车道B12014年03月13日01时30分30秒RX09D1H1驶向北至南违章超速时速61公里_S.JPG"));

        assertThat(image.getImageType(), is(ImageType.CROSS));
        assertThat(image.getViolationType(), is(ViolationType.OVER_SPEED));
        assertThat(image.getImageExt(), is("JPG"));

        assertThat(image.getGeo().getDirection(), is("从南至北"));
        assertThat(image.getGeo().getLatitude(), is(1574L));
        assertThat(image.getGeo().getLongitude(), is(99L));

        assertThat(image.getCapturedAt(), is(ImageUtils.parseDate("2014年03月13日01时30分30秒").toDate()));


    }

    @Test
    public void testJson() throws Exception {

        String json = Resources.toString(Resources.getResource("request-image.json"), Charsets.UTF_8);
        List<Image> images = new ImageParser().parse(json);
        assertThat(images.size() > 0, is(true));

        Image image = images.get(0);

        System.out.println( new Gson().toJson(image));
    }
}
