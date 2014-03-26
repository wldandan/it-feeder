package com.it.epolice.sync.db;

import com.it.epolice.domain.Image;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.VersionHelper;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ImageDAO extends BasicDAO<Image, ObjectId> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDAO.class);

    public ImageDAO(Mongo mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    public Image get(String imageId) {
        return this.findOne("image_id", imageId);
    }


    public Boolean saveOrUpdate(Image image) {
        Query<Image> query = createQuery().filter("image_id", image.getImageId());
        LOGGER.info("Saving image " + image.getTitle() + "to database!");
        image.setUpdatedAt(new Date());
        getDatastore().updateFirst(query, image, true);
        return true;
    }
}
