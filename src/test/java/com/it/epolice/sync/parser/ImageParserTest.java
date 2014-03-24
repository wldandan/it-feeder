package com.it.epolice.sync.parser;

import com.google.common.io.Resources;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.it.epolice.domain.Image;
import com.it.epolice.domain.ImageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(MockitoJUnitRunner.class)
public class ImageParserTest {

    @Test
    public void testParse() throws Exception {
        String json = Resources.toString(Resources.getResource("request-image001.json"), Charsets.UTF_8);
        List<Image> images = new ImageParser().parse(json);
        assertThat(images.size(), is(1));

        Image image = images.get(0);
        assertThat(image.getName(), is("001"));

        assertThat(image.getVehicle().getNumber(), is("陕A01L01"));
        assertThat(image.getImageType(), is(ImageType.CROSS));

    }

    @Test
    public void testEnumValues() throws Exception {
//        assertNotNull(ImageType.valueOf("CROSS"));
        assertNotNull(ImageType.valueOf("CROSS"));


    }

    @Test
    public void testJson() throws Exception {

        String json = Resources.toString(Resources.getResource("request-image001.json"), Charsets.UTF_8);
        List<Image> images = new ImageParser().parse(json);
        assertThat(images.size(), is(1));

        Image image = images.get(0);

        System.out.println( new Gson().toJson(image));
    }

}
