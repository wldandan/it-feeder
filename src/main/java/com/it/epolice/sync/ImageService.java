package com.it.epolice.sync;


import com.it.epolice.domain.Image;
import com.it.epolice.domain.PersistStatus;
import com.it.epolice.sync.db.dao.ImageDAO;
import com.it.epolice.sync.fs.ImageStore;
import com.it.epolice.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageDAO imageDAO;

    @Autowired
    private ImageStore imageStore;

    private PersistStatus sync(Image image) {
        try {
            if (!imageStore.generate(image)) {
                return PersistStatus.UN_STORED;
            }

            return imageDAO.saveOrUpdate(image)? PersistStatus.SAVED : PersistStatus.UN_SAVED;

        } catch (Exception e) {
            LOGGER.error("sync image" + image.getImageId() + " failed");
            return PersistStatus.FAILED;
        }
    }

    public Map<String, PersistStatus> sync(List<Image> images){

        Map<String, PersistStatus> results = newHashMap();

        imageStore.start();

        for (Image image : images) {
            image.setDistributedPath(ImageUtils.generateDistributedPath(image));
            results.put(image.getImageId(), sync(image));
        }

        imageStore.stop();

        return results;
    }

    public List<Image> getImagesByCarNoAndDuration(){return newArrayList();}


}
