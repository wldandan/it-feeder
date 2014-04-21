package com.it.epolice.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ImageUtilsTest {
    @Test
    public void testParseDate() throws Exception {
        String source = "2014年03月13日09时45分03秒";

        DateTime dateTime = ImageUtils.parseDate(source);

        assertThat(dateTime.getYear(), is(2014));
        assertThat(dateTime.getMonthOfYear(), is(03));
        assertThat(dateTime.getDayOfMonth(), is(13));
        assertThat(dateTime.getHourOfDay(), is(9));
        assertThat(dateTime.getMinuteOfHour(), is(45));
        assertThat(dateTime.getSecondOfMinute(), is(03));
    }

    @Test
    public void testUTCDateConversation() throws Exception {
        System.out.println(new DateTime(1391246632 * 1000L, DateTimeZone.UTC).toString());
    }
}
