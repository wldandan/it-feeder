package com.it.epolice.solr;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static com.mongodb.util.MyAsserts.assertTrue;

public class SolrDocumentBuilderTest {

    private HttpSolrServer solr;

    @Before
    public void setUp() throws Exception {
        this.solr = new HttpSolrServer("http://localhost:8984/solr");
    }

    @Test
    public void testAddDocumentToSolr() throws Exception {
        String result = index();
        System.out.println(result);
        assertTrue(!result.isEmpty());
    }

    private SolrInputDocument build(){
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "wldandan");
        doc.addField("sku", "au" + new DateTime().toString());
        return doc;
    }

    private String index(){
        String result = "";
        try {
            solr.add(this.build());
            solr.add(this.build());
            solr.add(this.build());
            solr.add(this.build());
            solr.add(this.build());

            result = solr.commit().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
