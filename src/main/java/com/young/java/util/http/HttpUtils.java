package com.young.java.util.http;

import com.young.scala.weixin.entity.WeixinResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUtils {

	private HttpClient client;

	private String sendCharset = "utf-8";

	private int timeout = 1000;

	public HttpUtils() {
		this.client = HttpClients.createDefault();
	}

	public HttpUtils(String sendCharset, int timeout) {
		this();
		this.sendCharset = sendCharset;
		this.timeout = timeout;
	}

	
	public WeixinResponse sendRequest(String uri, HttpMethod method,
			Map<String, String> params) throws UnsupportedOperationException,
			IOException {
		HttpUriRequest request = createRequest(uri, method, params);
		HttpResponse response = client.execute(request);
		WeixinResponse weixinResponse = new WeixinResponse();
		weixinResponse.setContent(IOUtils.toString(response.getEntity().getContent(), sendCharset));
		weixinResponse.setMessage(response.getStatusLine().getReasonPhrase());
		weixinResponse.setStateCode(response.getStatusLine().getStatusCode());
		return weixinResponse;
	}

	private HttpUriRequest createRequest(String uri, HttpMethod method,
			Map<String, String> params) throws UnsupportedEncodingException {
		HttpEntity entity = null;
		if (HttpMethod.GET == method) {
			HttpGet get = new HttpGet(uri);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeout).setConnectTimeout(timeout)
					.build();
			get.setConfig(requestConfig);
			return get;
		} else if (HttpMethod.PUT == method) {
			HttpPut put = new HttpPut(uri);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeout).setConnectTimeout(timeout)
					.build();
			put.setConfig(requestConfig);
			entity = createEntity(params);
			if (entity != null)
				put.setEntity(entity);
			return put;
		} else if (HttpMethod.DELETE == method) {
			HttpDelete delete = new HttpDelete(uri);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeout).setConnectTimeout(timeout)
					.build();
			delete.setConfig(requestConfig);
			return delete;
		} else if (HttpMethod.POST == method) {
			HttpPost post = new HttpPost(uri);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeout).setConnectTimeout(timeout)
					.build();
			post.setConfig(requestConfig);
			entity = createEntity(params);
			if (entity != null)
				post.setEntity(entity);
			return post;
		} else {
			HttpGet get = new HttpGet(uri);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeout).setConnectTimeout(timeout)
					.build();
			get.setConfig(requestConfig);
			return get;
		}

	}

	private HttpEntity createEntity(Map<String, String> params)
			throws UnsupportedEncodingException {
		if (params == null) {
			return null;
		}
		List<NameValuePair> args = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			args.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		HttpEntity entity = new UrlEncodedFormEntity(args, sendCharset);
		return entity;
	}
}
