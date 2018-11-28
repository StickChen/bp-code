package com.shallop.bpc.collection.web;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URI;

/**
 * @author chenxuanlong
 * @date 2016/10/28
 */
public class HttpClient4Demo {

	private HttpClient httpClient;

	@Test
	public void test4() {
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			HttpGet get = new HttpGet();
			String url = "http://hc.apache.org/";
			get.setURI(new URI(url));
			response = getHttpClient().execute(get);
			// 处理响应
		} catch (Exception e) {
			// 处理异常
		} finally {
			if (response != null) {
				EntityUtils.consumeQuietly(response.getEntity()); // 会自动释放连接
			}
			// 如下方法也是可以的，但是存在一些风险；不要用
			// InputStream is = response.getEntity().getContent();
			// is.close();
		}
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}
}
