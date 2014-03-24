package com.it.epolice.domain;

import java.io.Serializable;

public enum ImageType implements Serializable {
    CROSS("cross"), BAYONET("bayonet"), NONE("none");

    private String value;

    ImageType(String value) {
        this.value = value;
    }



//    public static ImageType parse(String imageType) {
//        for (ImageType image : valueOf()) {
//            if (match(featureType, enumValue)) {
//                return enumValue;
//            }
//        }
//        return null;
//    }


}
