package com.nostra13.universalimageloader.core.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;

import com.nostra13.universalimageloader.utils.Base64;

public class HttpClientImageDownloader extends ImageDownloader {

	private HttpClient httpClient;

	public HttpClientImageDownloader(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	protected InputStream getStreamFromNetwork(URI imageUri, String userAgent, String userName, String passWord) throws IOException {
		HttpGet httpRequest = new HttpGet(imageUri.toString());
		if (userAgent != null) {
		httpRequest.setHeader("User-Agent", userAgent);
		}
		if (userName != null && passWord != null) {
		httpRequest.setHeader(
				"Authorization",
				"basic "
						+ Base64.encodeBytes((userName + passWord)
								.getBytes()));
		}
		HttpResponse response = httpClient.execute(httpRequest);
		HttpEntity entity = response.getEntity();
		BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
		return bufHttpEntity.getContent();
	}
}
