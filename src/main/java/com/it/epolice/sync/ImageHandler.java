package com.it.epolice.sync;

import com.it.epolice.domain.*;

import java.lang.Boolean;

public interface ImageHandler {

    public Boolean handle(Image image);
    public int getSuccessCode();
}
