package com.it.epolice.utils;

import com.it.epolice.domain.Image;
import org.joda.time.DateTime;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;

public class ImageUtils {

    public static String generateDistributedPath(Image image){
        return "/tmp/image" + image.getImageId() + '.' + image.getDistributedPath();
    }

    public static DateTime parseDate(String source){
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(source);
        List<Integer> values = newArrayList();
        while (m.find()){
            values.add(Integer.valueOf(m.group()));
        }
        return new DateTime(
                values.get(0),
                values.get(1),
                values.get(2),
                values.get(3),
                values.get(4),
                values.get(5));
    }

}
