package com.it.epolice.sync.solr;

import com.it.epolice.domain.Image;
import com.it.epolice.domain.ImageStatus;
import com.it.epolice.sync.ImageHandler;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SolrIndexingService implements IndexingService, ImageHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(SolrIndexingService.class);

    private final HttpSolrServer solr;
    private SolrDocumentBuilder builder;

    public SolrIndexingService(final String serverUrl, SolrDocumentBuilder builder) {
        this.solr = new HttpSolrServer(serverUrl);
        this.builder = builder;
    }

    @Override
    public Boolean handle(Image image) throws Exception {
        index(image, true);
        return true;
    }

    @Override
    public int getSuccessCode() {
        return ImageStatus.INDEXED.getCode();
    }

    @Override
    public void index(Image image) {
        index(image, true);
    }

    @Override
    public void index(Iterable<Image> images) {
        for (Image image : images) {
            index(image, false);
        }
        commit();
    }

    @Override
    public void delete(String imageId) {
        try {
            solr.deleteById(imageId);
        } catch (SolrServerException e) {
            throw new IndexingException("Failed to delete image " + imageId, e);
        } catch (IOException e) {
            throw new IndexingException("Failed to delete image " + imageId, e);
        }
        commit();
    }

    private void index(Image image, boolean commit) {
        if (image == null) {
            return;
        }

        SolrInputDocument doc = builder.build(image);
        try {
            solr.add(doc);
        } catch (SolrServerException e) {
            throw new IndexingException("Failed to index image " + image.getImageId(), e);
        } catch (IOException e) {
            throw new IndexingException("Failed to index image " + image.getImageId(), e);
        }

        if (commit) {
            commit();
        }
    }

    private void commit() {
        try {
            solr.commit();
        } catch (SolrServerException e) {
            throw new IndexingException("Failed to commit index change", e);
        } catch (IOException e) {
            throw new IndexingException("Failed to commit index change", e);
        }
    }
}