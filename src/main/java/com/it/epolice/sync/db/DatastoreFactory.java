package com.it.epolice.sync.db;

import com.mongodb.WriteConcern;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;


import static com.it.epolice.utils.MongoUtils.createMongoClient;

public class DatastoreFactory {

    private WriteConcern writeConcern = WriteConcern.ACKNOWLEDGED;

    public DatastoreFactory(String writeConcern) {
//        if (StringUtils.isNotBlank(writeConcern)) {
            this.writeConcern = WriteConcern.valueOf(writeConcern);
//        }
    }

    public Datastore createDatastore(Morphia morphia, String dbHost, String dbName) {
        Datastore datastore = morphia.createDatastore(createMongoClient(dbHost), dbName);
        datastore.setDefaultWriteConcern(writeConcern);
        datastore.ensureIndexes();
        return datastore;
    }
}
