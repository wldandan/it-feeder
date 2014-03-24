package com.it.epolice.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoUtils {

    /**
     *
     * @param hosts format: host[:port][,host[:port]]
     * @return
     */
    public static MongoClient createMongoClient(String hosts) {
        try {
            return buildReplicaSetClient(hosts);
        } catch (UnknownHostException e) {
            throw new RuntimeException("failed to create mongo client", e);
        }
    }

    private static MongoClient buildReplicaSetClient(String databaseConnection) throws UnknownHostException {
        List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
        for (String connection : databaseConnection.split(",")) {
            serverAddresses.add(parseServerAddress(connection));
        }
        return new MongoClient(serverAddresses, MongoClientOptions.builder().build());
    }

    private static ServerAddress parseServerAddress(String connection) throws UnknownHostException {
        String[] parts = connection.split(":");
        if (parts.length == 1) {
            return new ServerAddress(connection);
        }
        if (parts.length == 2) {
            String host = parts[0].trim();
            String port = parts[1].trim();
            return new ServerAddress(host, Integer.parseInt(port));
        }
        throw new IllegalArgumentException(String.format("Invalid mongo connection string: %s, expected format host:port", connection));
    }
}
