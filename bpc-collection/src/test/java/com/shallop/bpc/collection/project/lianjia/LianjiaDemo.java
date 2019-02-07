package com.shallop.bpc.collection.project.lianjia;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @author chenxl
 * @date 2018/4/15
 */
public class LianjiaDemo {

    OkHttpClient client = new OkHttpClient();
    
    @Test
    public void testLianjiaDemo() throws IOException {

        long time = new Date().getTime();


        HttpUrl httpUrl = new HttpUrl.Builder()
				.host("https://ajax.lianjia.com/map/search/ershoufang/?callback=jQuery111102227062513737328_1523780672745&city_id=110000&group_type=bizcircle&max_lat=39.748298&min_lat=39.716558&max_lng=116.311932&min_lng=116.091165&filters=%7B%7D&source=ljpc")
                .addQueryParameter("request_ts", "")
                .addQueryParameter("authorization", "")
                // Each addPathSegment separated add a / symbol to the final url
                // finally my Full URL is: 
                // https://subdomain.apiweb.com/api/v1/students/8873?auth_token=71x23768234hgjwqguygqew
                .build();
        
        String url = "https://ajax.lianjia.com/map/search/ershoufang/?callback=jQuery111102227062513737328_1523780672745&city_id=110000&group_type=bizcircle&max_lat=39.748298&min_lat=39.716558&max_lng=116.311932&min_lng=116.091165&filters=%7B%7D&source=ljpc";
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        response.body().string();
        
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Test
    public void post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        response.body().string();
    }
}
