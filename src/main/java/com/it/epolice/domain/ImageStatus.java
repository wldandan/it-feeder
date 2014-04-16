package com.it.epolice.domain;

public enum ImageStatus {
    NONE(0), DISTRIBUTED(1), SAVED(2), INDEXED(4);

    private int code = 0;

    ImageStatus(int code) {
        this.code = code;
    }

    public Integer getCode(){
        return this.code;
    }
}
