package com.it.epolice.sync.solr;

import com.it.epolice.domain.Image;
import org.apache.solr.common.SolrInputDocument;

public class SolrDocumentBuilder {

    public SolrInputDocument build(Image image) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", image.getImageId());
        doc.addField("vehicle_number", image.getVehicle().getNumber());
        doc.addField("title", image.getTitle());
        doc.addField("path", image.getPath());
        doc.addField("distributed_path", image.getDistributedPath());

        doc.addField("captured_at", image.getCapturedAt());
        doc.addField("description", image.getDescription());

        return doc;
    }
}