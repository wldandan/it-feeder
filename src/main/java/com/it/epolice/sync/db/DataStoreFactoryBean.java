package com.it.epolice.sync.db;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.util.StringUtils;

public class DataStoreFactoryBean extends AbstractFactoryBean<Datastore> {

    private Morphia morphia;
    private Mongo mongo;
    private String dbName;
    private String user;
    private String password;
    private WriteConcern writeConcern = WriteConcern.ACKNOWLEDGED;

    @Override
    public Class<?> getObjectType() {
        return Datastore.class;
    }

    @Override
    protected Datastore createInstance() throws Exception {
        Datastore datastore;
        if (StringUtils.hasText(user)) {
            datastore = morphia.createDatastore(mongo, dbName, user, password.toCharArray());
        }
        else{
            datastore = morphia.createDatastore(mongo, dbName);
        }
        datastore.setDefaultWriteConcern(writeConcern);
        return  datastore;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (mongo == null) {
            throw new IllegalStateException("mongo is not set");
        }
        if (morphia == null) {
            throw new IllegalStateException("morphia is not set");
        }
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}