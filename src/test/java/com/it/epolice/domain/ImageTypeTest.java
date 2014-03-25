package com.it.epolice.domain;

import org.junit.Before;
import org.junit.Test;

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


}
