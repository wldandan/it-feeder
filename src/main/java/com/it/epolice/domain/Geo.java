package com.it.epolice.domain;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Indexed;

@Embedded
public class Geo {

    private Long longitude;

    private Long latitude;

    private String direction;

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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
