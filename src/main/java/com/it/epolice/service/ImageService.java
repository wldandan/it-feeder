package com.it.epolice.service;


import com.it.epolice.domain.Image;
import com.it.epolice.domain.ImageStatus;
import com.it.epolice.sync.db.ImageDAO;
import com.it.epolice.sync.fs.ImageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageDAO imageDAO;

    @Autowired
    private ImageStore imageStore;

    private ImageStatus sync(Image image) {
        try {
            if (!imageStore.generate(image)) {
                return ImageStatus.FAILED;
            }

            return imageDAO.saveOrUpdate(image)? ImageStatus.SAVED : ImageStatus.STORED;

        } catch (Exception e) {
            LOGGER.error("sync image" + image.getImageId() + " failed");
            return ImageStatus.FAILED;
        }
    }

    public Map<String, ImageStatus> sync(List<Image> images){

        Map<String, ImageStatus> results = newHashMap();

        imageStore.start();

        for (Image image : images) {
            image.setDistributedPath("/tmp/"+image.getImageId()+image.getExtension());
            results.put(image.getImageId(), sync(image));
        }

        imageStore.stop();

        return results;
    }
}
