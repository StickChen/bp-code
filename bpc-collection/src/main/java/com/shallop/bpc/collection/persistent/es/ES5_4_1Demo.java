package com.shallop.bpc.collection.persistent.es;

import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.junit.Test;

import static com.shallop.bpc.collection.utils.Printer.pts;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author StickChen
 * @date 2017/6/4
 */
public class ES5_4_1Demo {


    @Test
    public void testFilter(){

        ESClient.client.prepareSearch("emp").setQuery(boolQuery().filter(termQuery("", "")));

    }

    @Test
    public void testIndex(){

        GetIndexResponse response = ESClient.client.admin().indices().prepareGetIndex().get();
        pts(response);

    }

    @Test
    public void testQueryAfter(){

//        ESClient.client.prepareSearch("").searchAfter();

    }
}
