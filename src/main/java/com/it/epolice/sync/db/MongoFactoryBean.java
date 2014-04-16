package com.it.epolice.sync.db;

import com.mongodb.*;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.ArrayList;
import java.util.List;

public class MongoFactoryBean extends AbstractFactoryBean<Mongo> {
  
    private List<ServerAddress> replicaSetSeeds = new ArrayList<ServerAddress>();
    private MongoClientOptions mongoOptions;
  
    @Override  
    public Class<?> getObjectType() {  
        return Mongo.class;  
    }  
  
    @Override  
    protected Mongo createInstance() throws Exception {
        if (replicaSetSeeds.size() > 0) {  
            if (mongoOptions != null) {  
                return new MongoClient(replicaSetSeeds, mongoOptions);

            }  
            return new MongoClient(replicaSetSeeds);
        }  
        return new MongoClient();
    }  
  
    public void setMultiAddress(String[] serverAddresses) {  
        replSeeds(serverAddresses);  
    }  
  
    private void replSeeds(String... serverAddresses) {  
        try {  
            replicaSetSeeds.clear();  
            for (String addr : serverAddresses) {  
                String[] a = addr.split(":");  
                String host = a[0];  
                if (a.length > 2) {  
                    throw new IllegalArgumentException("Invalid Server Address : " + addr);  
                }else if(a.length == 2) {  
                    replicaSetSeeds.add(new ServerAddress(host, Integer.parseInt(a[1])));  
                }else{  
                    replicaSetSeeds.add(new ServerAddress(host));  
                }  
            }  
        } catch (Exception e) {  
            throw new BeanCreationException("Error while creating replicaSetAddresses",e);
        }  
    }  
  
    public void setAddress(String serverAddress) {  
        replSeeds(serverAddress);  
    }  
  
} 