package com.it.epolice.domain;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Indexed;

import java.io.Serializable;

@Embedded
public class Vehicle implements Serializable{

    @Indexed(unique = true)
    private String number;

    private String color;

    private String description;

    private Integer speed;

    private String unit;

    public void setNumber(String number) {
        this.number = number;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }
}
