package com.it.epolice.sync;


import com.it.epolice.domain.*;
import com.it.epolice.sync.db.dao.ImageDAO;
import com.it.epolice.sync.fs.Distributor;
import com.it.epolice.sync.solr.SolrIndexingService;
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

    private List<ImageHandler> handlers = newArrayList();

    public ImageService(List<ImageHandler> handlers) {
        this.handlers = handlers;
    }

    private Integer sync(Image image){
        Integer statusCode = 0;

        for (ImageHandler imageHandler : handlers) {
            try {
                if (imageHandler.handle(image)){
                    statusCode +=  imageHandler.getSuccessCode();
                }
            } catch (Exception e) {
                LOGGER.warn("There is an error when handle image " + image.getImageId() + "[" + image.getSource() + "]");
                return statusCode;
            }
        }
        return statusCode;
    }

    public Map<String, Integer> sync(List<Image> images){
        Map<String, Integer> results = newHashMap();

        for (Image image :images) {
            results.put(image.getImageId(), sync(image));
        }
        return results;
    }

    public List<Image> getImages(){
        return ((ImageDAO)handlers.get(1)).queryImages().asList();
    }
}
