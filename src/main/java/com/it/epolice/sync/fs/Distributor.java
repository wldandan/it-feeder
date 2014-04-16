package com.it.epolice.sync.fs;

import com.it.epolice.domain.Image;

public interface Distributor {
    public Boolean distribute(Image image) throws Exception;

    void start() throws Exception;

    void stop();
}
