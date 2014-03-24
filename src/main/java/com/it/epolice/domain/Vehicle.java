package com.it.epolice.domain;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Indexed;

import java.io.Serializable;

@Embedded
public class Vehicle implements Serializable{

    @Indexed(unique = true)
    private String number;
    private String color;

    public String getNumber() {
        return number;
    }

    public String getColor() {
        return color;
    }
}
