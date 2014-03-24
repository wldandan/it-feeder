package com.it.epolice.domain;

import java.io.Serializable;

public enum ViolationType implements Serializable {
    RUN_RED_LIGHT("run_red_light"), OVER_SPEED("over_speed"), NONE("none");

    private String identity;

    ViolationType(String value) {
        this.identity = value;
    }
}
