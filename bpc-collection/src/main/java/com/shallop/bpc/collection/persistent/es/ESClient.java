package com.shallop.bpc.collection.persistent.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author StickChen
 * @date 2017/6/4
 */
public class ESClient {

    public static TransportClient client;

    static {


        try {
            Settings settings = Settings.builder()
//                    .put("cluster.name", "myClusterName")
                    .put("client.transport.sniff", true).build();
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
