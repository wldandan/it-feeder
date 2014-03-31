//package com.it.epolice.sync.solr;
//
//import com.google.gson.Gson;
//import com.it.epolice.domain.Image;
//import org.apache.solr.common.SolrInputDocument;
//
//public class SolrDocumentBuilder {
//
//    private Gson gson;
//
//    public SolrDocumentBuilder() {
////        this.gson = buildGson();
//    }
//
//    public SolrInputDocument build(Image image) {
//        SolrInputDocument doc = new SolrInputDocument();
//        return doc;
//    }
//
////    private Gson buildGson() {
////        GsonBuilder builder = new GsonBuilder();
////        builder.registerTypeAdapter(Address.class, new JsonSerializer<Address>() {
////            @Override
////            public JsonElement serialize(Address src, Type typeOfSrc, JsonSerializationContext context) {
////                JsonObject json = new JsonObject();
////                json.addProperty("postalcode", src.getPostcode());
////                json.addProperty("streetaddr", src.getStreet());
////                json.addProperty("country", src.getCountry());
////                json.addProperty("city", src.getLocality().getName());
////                json.addProperty("state", src.getSubdivision().getName());
////                return json;
////            }
////        });
////        return builder.create();
//    }
//}
