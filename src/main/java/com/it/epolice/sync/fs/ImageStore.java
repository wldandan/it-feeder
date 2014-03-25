package com.it.epolice.sync.fs;

import com.it.epolice.domain.Image;

public interface ImageStore {
    public Boolean generate(Image image) throws Exception;

    void start();

    void stop();
}
