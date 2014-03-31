package com.it.epolice.domain;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Indexed;

@Embedded
public class GeoLocation {

    @Indexed(unique = true)
    private Long longitude;

    @Indexed(unique = true)
    private Long latitude;

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }
}
