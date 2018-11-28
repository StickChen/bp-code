package com.shallop.bpc.collection.web;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 * @author chenxuanlong
 * @date 2016/10/28
 */
public class HttpClient3Demo {

	private HttpClient httpClient;

	@Test
	public void test3(){
		PostMethod postMethod = new PostMethod("http://www.baidu.com");
		try {
			httpClient.executeMethod(postMethod);
		} catch (Exception e) {
			//处理异常
		} finally {
			if(postMethod != null) { //不要忘记释放，尽量通过该方法实现，
				postMethod.releaseConnection();
				//存在风险，不要用
				//postMethod.setParameter("Connection", "close");
				//InputStream is = postMethod.getResponseBodyAsStream();
				//is.clsoe();也会关闭并释放连接的
			}
		}
	}


}
