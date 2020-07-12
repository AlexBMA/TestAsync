package com.test.app.smartbox;

import org.apache.solr.common.SolrInputDocument;

public class SmartBoxMapper {

    public static SolrInputDocument createSmartDocument (SmartDocument smartDocument){
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.setField("id", System.currentTimeMillis() + Math.random());
        solrInputDocument.setField("name", smartDocument.getName());
        solrInputDocument.setField("type", smartDocument.getType());

        return solrInputDocument;
    }
}
