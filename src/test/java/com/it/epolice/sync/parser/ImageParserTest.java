package com.it.epolice.sync.parser;

import com.google.common.io.Resources;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.it.epolice.domain.Image;
import com.it.epolice.domain.ImageType;
import com.it.epolice.domain.PersistStatus;
import com.it.epolice.domain.ViolationType;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
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

        assertThat(image.getDirection(),is("从南至北"));
        //assertThat(image.getCapturedAt().toString(),is("2014-03-05T03:46:56Z"));

        assertThat(image.getImageType(), is(ImageType.CROSS));
        assertThat(image.getViolationType(), is(ViolationType.OVER_SPEED));

        assertThat(image.getGeoLocation().getLatitude(), is(1574L));
        assertThat(image.getGeoLocation().getLongitude(), is(99L));

        assertThat(image.getExtension(), is("JPG"));

        assertThat(image.getPersistStatus(), is(PersistStatus.NONE));
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
