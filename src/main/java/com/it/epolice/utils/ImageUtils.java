package com.it.epolice.utils;

import com.it.epolice.domain.Image;

public class ImageUtils {

    public static String generateDistributedPath(Image image){
        return "/tmp/" + image.getImageId() + '.' + image.getExtension();
    }
}
