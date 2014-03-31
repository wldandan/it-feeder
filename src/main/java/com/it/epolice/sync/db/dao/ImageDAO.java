package com.it.epolice.sync.db.dao;

import com.google.common.base.Throwables;
import com.it.epolice.domain.Image;
import com.mongodb.Mongo;
import org.apache.commons.beanutils.PropertyUtils;
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

    public void save(Image image, String... fields) {
        String imageId = image.getImageId();
        Query<Image> query = createQuery().filter("image_id", imageId);

        UpdateOperations<Image> update = createUpdateOperations();
        update.set("lock", VersionHelper.nextValue(image.getLock()));
        update.set("updatedAt", new Date());

        for (String field : fields) {
            try {
                Object value = PropertyUtils.getProperty(image, field);
                update.set(field, value);
            } catch (Exception e) {
                throw Throwables.propagate(e);
            }
        }
        getDatastore().update(query, update);
    }


    public Boolean saveOrUpdate(Image image) {
        Query<Image> query = createQuery().filter("image_id", image.getImageId());
        if (exists(query)){
            image.setUpdatedAt(new Date());
            LOGGER.info("Updating existing image " + image.getTitle() + "to database!");
        }
        else{
            image.setCreatedAt(new Date());
            image.setUpdatedAt(new Date());
            LOGGER.info("Saving new image " + image.getTitle() + "to database!");
        }
        getDatastore().updateFirst(query, image, true);
        return true;
    }

    public void delete(String imageId) {
        Query<Image> query = createQuery().filter("image_id", imageId);
        UpdateOperations<Image> update = createUpdateOperations();
        update.set("deleted", true);
        updateFirst(query, update);
    }

}
