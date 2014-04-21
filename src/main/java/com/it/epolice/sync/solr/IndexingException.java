package com.it.epolice.sync.solr;

public class IndexingException extends RuntimeException {

    public IndexingException(String message, Exception cause) {
        super(cause);
    }

}
