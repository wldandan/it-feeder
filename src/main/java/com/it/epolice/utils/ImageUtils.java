package com.it.epolice.utils;

import com.it.epolice.domain.Image;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

public class ImageUtils {

    public static String generateDistributedPath(Image image){
        return "/tmp/" + image.getImageId() + '.' + image.getDistributedPath();
    }

    public static DateTime parseDate(String source){
        String date = source.replace("年","-").replace("月", "-").replace("日", " ")
                .replace("时", ":").replace("分", ":").replace("秒", "");
        return DateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
