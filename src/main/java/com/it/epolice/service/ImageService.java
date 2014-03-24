package com.it.epolice.service;


import com.it.epolice.domain.Image;
import com.it.epolice.sync.db.ImageDAO;
import com.it.epolice.sync.fs.ImageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageDAO imageDAO;

    @Autowired
    private ImageStore imageStore;

    private void sync(Image image) throws Exception {
        if (imageStore.generate(image)) {
            imageDAO.saveOrUpdate(image);
        }
    }

    public void sync(List<Image> images) throws Exception {
        imageStore.start();

        for (Image image : images) {
            sync(image);
        }

        imageStore.stop();
    }
}
