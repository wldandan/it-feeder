//package com.it.epolice.sync.solr;
//
//import com.google.inject.Singleton;
//import com.google.inject.name.Named;
//import com.reagroup.china.listing.Listing;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.common.SolrInputDocument;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.inject.Inject;
//import java.io.IOException;
//
//@Singleton
//public class SolrIndexingService implements IndexingService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(SolrIndexingService.class);
//
//    private final HttpSolrServer solr;
//    private SolrDocumentBuilder builder;
//
//    public SolrIndexingService(@Named("solr.url") final String serverUrl, SolrDocumentBuilder builder) {
//        this.solr = new HttpSolrServer(serverUrl);
//        this.builder = builder;
//    }
//
//    @Override
//    public void index(Listing listing) {
//        index(listing, true);
//    }
//
//    @Override
//    public void index(Iterable<Listing> listings) {
//        for (Listing listing : listings) {
//            index(listing, false);
//        }
//        commit();
//    }
//
//    @Override
//    public void delete(String listingId) {
//        try {
//            solr.deleteById(listingId);
//        } catch (SolrServerException e) {
//            throw new IndexingException("Failed to delete listing " + listingId, e);
//        } catch (IOException e) {
//            throw new IndexingException("Failed to delete listing " + listingId, e);
//        }
//        commit();
//    }
//
//    private void index(Listing listing, boolean commit) {
//        if (listing == null) {
//            return;
//        }
//        if (!listing.publishable()) {
//            LOGGER.info("Skip un-publishable listing: " + listing.getListingId());
//            return;
//        }
//
//        SolrInputDocument doc = builder.build(listing);
//        try {
//            solr.add(doc);
//        } catch (SolrServerException e) {
//            throw new IndexingException("Failed to index listing " + listing.getListingId(), e);
//        } catch (IOException e) {
//            throw new IndexingException("Failed to index listing " + listing.getListingId(), e);
//        }
//
//        if (commit) {
//            commit();
//        }
//    }
//
//    private void commit() {
//        try {
//            solr.commit();
//        } catch (SolrServerException e) {
//            throw new IndexingException("Failed to commit index change", e);
//        } catch (IOException e) {
//            throw new IndexingException("Failed to commit index change", e);
//        }
//    }
//}