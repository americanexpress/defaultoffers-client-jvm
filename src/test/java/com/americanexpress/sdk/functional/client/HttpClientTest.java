/*
 * Copyright 2020 American Express Travel Related Services Company, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.americanexpress.sdk.functional.client;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import com.americanexpress.sdk.exception.*;
import com.americanexpress.sdk.exception.DefaultOffersApiError;
import com.americanexpress.sdk.exception.DefaultOffersAuthenticationError;
import com.americanexpress.sdk.models.default_offers.OffersResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.americanexpress.sdk.client.core.utils.DefaultOffersUtil;
import com.americanexpress.sdk.client.http.HttpClient;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DefaultOffersUtil.class })
public class HttpClientTest {

	private CloseableHttpClient closeableHttpClient;
	private HttpClient httpClient;
	private HttpEntity httpEntity;
	private String apiUrl;
	private MultivaluedMap<String, Object> headers;
	private Map<String, String> responseHeaders;
	private CloseableHttpResponse response;
	private StatusLine statusLine;

	@Before
	public void setUp() throws Exception {
		closeableHttpClient = EasyMock.createNiceMock(CloseableHttpClient.class);
		httpClient = new HttpClient(closeableHttpClient);
		httpEntity = EasyMock.createNiceMock(HttpEntity.class);
		apiUrl = "apiUrl";
		headers = new MultivaluedHashMap<>();
		responseHeaders = new HashMap<>();
		response = EasyMock.createNiceMock(CloseableHttpResponse.class);
		statusLine = EasyMock.createNiceMock(StatusLine.class);

		EasyMock.expect(closeableHttpClient.execute(EasyMock.isA(HttpPost.class))).andReturn(response);
		EasyMock.expect(closeableHttpClient.execute(EasyMock.isA(HttpGet.class))).andReturn(response);
		EasyMock.replay(closeableHttpClient);

		EasyMock.expect(response.getEntity()).andReturn(httpEntity).anyTimes();
		EasyMock.expect(response.getStatusLine()).andReturn(statusLine).anyTimes();
		EasyMock.expect(response.getAllHeaders()).andReturn(new Header[0]);
		EasyMock.replay(response);

		PowerMock.mockStatic(DefaultOffersUtil.class);
	}

	@Test
	public void testPostClientResource() throws Exception {
		EasyMock.expect(statusLine.getStatusCode()).andReturn(200);
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.generateResponse(EasyMock.anyObject(), EasyMock.isA(CloseableHttpResponse.class)))
				.andReturn(new OffersResponse());
		PowerMock.replay(DefaultOffersUtil.class);

		OffersResponse result = httpClient.postClientResource(
				httpEntity, apiUrl, headers, new TypeReference<OffersResponse>() {}, responseHeaders);
		assertNotNull(result);
	}

	@Test (expected = DefaultOffersResourceNotFoundError.class)
	public void testPostClientResource_ResourceNotFound() throws Exception{
		EasyMock.expect(statusLine.getStatusCode()).andReturn(404).anyTimes();
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.getResponseString(EasyMock.isA(HttpEntity.class)))
				.andReturn("response string");
		PowerMock.replay(DefaultOffersUtil.class);

		httpClient.postClientResource(httpEntity, apiUrl, headers,
				new TypeReference<OffersResponse>() {}, responseHeaders);
	}

	@Test (expected = DefaultOffersRequestValidationError.class)
	public void testPostClientResource_RequestValidationError() throws Exception{
		EasyMock.expect(statusLine.getStatusCode()).andReturn(400).anyTimes();
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.getResponseString(EasyMock.isA(HttpEntity.class)))
				.andReturn("response string");
		PowerMock.replay(DefaultOffersUtil.class);

		httpClient.postClientResource(httpEntity, apiUrl, headers,
				new TypeReference<OffersResponse>() {}, responseHeaders);
	}

	@Test (expected = DefaultOffersAuthenticationError.class)
	public void testPostClientResource_AuthenticationError() throws Exception{
		EasyMock.expect(statusLine.getStatusCode()).andReturn(401).anyTimes();
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.getResponseString(EasyMock.isA(HttpEntity.class)))
				.andReturn("response string");
		PowerMock.replay(DefaultOffersUtil.class);

		httpClient.postClientResource(httpEntity, apiUrl, headers,
				new TypeReference<OffersResponse>() {}, responseHeaders);
	}

	@Test (expected = DefaultOffersApiError.class)
	public void testPostClientResource_DefaultOffersApiError() throws Exception{
		EasyMock.expect(statusLine.getStatusCode()).andReturn(500).anyTimes();
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.getResponseString(EasyMock.isA(HttpEntity.class)))
				.andReturn("response string");
		PowerMock.replay(DefaultOffersUtil.class);

		httpClient.postClientResource(httpEntity, apiUrl, headers,
				new TypeReference<OffersResponse>() {}, responseHeaders);
	}

	@Test
	public void testGetClientResource() throws Exception {
		EasyMock.expect(statusLine.getStatusCode()).andReturn(200);
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.generateResponse(EasyMock.anyObject(), EasyMock.isA(CloseableHttpResponse.class)))
				.andReturn(new OffersResponse());
		PowerMock.replay(DefaultOffersUtil.class);

		OffersResponse result = httpClient.getClientResource(
				apiUrl, headers, new TypeReference<OffersResponse>() {}, responseHeaders);
		assertNotNull(result);
	}

	@Test (expected = DefaultOffersResourceNotFoundError.class)
	public void testGetClientResource_ResourceNotFound() throws Exception{
		EasyMock.expect(statusLine.getStatusCode()).andReturn(404).anyTimes();
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.getResponseString(EasyMock.isA(HttpEntity.class)))
				.andReturn("response string");
		PowerMock.replay(DefaultOffersUtil.class);

		httpClient.getClientResource(apiUrl, headers,
				new TypeReference<OffersResponse>() {}, responseHeaders);
	}

	@Test (expected = DefaultOffersRequestValidationError.class)
	public void testGetClientResource_RequestValidationError() throws Exception{
		EasyMock.expect(statusLine.getStatusCode()).andReturn(400).anyTimes();
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.getResponseString(EasyMock.isA(HttpEntity.class)))
				.andReturn("response string");
		PowerMock.replay(DefaultOffersUtil.class);

		httpClient.getClientResource(apiUrl, headers,
				new TypeReference<OffersResponse>() {}, responseHeaders);
	}

	@Test (expected = DefaultOffersAuthenticationError.class)
	public void testGetClientResource_AuthenticationError() throws Exception{
		EasyMock.expect(statusLine.getStatusCode()).andReturn(401).anyTimes();
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.getResponseString(EasyMock.isA(HttpEntity.class)))
				.andReturn("response string");
		PowerMock.replay(DefaultOffersUtil.class);

		httpClient.getClientResource(apiUrl, headers,
				new TypeReference<OffersResponse>() {}, responseHeaders);
	}

	@Test (expected = DefaultOffersApiError.class)
	public void testGetClientResource_DefaultOffersApiError() throws Exception{
		EasyMock.expect(statusLine.getStatusCode()).andReturn(500).anyTimes();
		EasyMock.replay(statusLine);

		EasyMock.expect(DefaultOffersUtil.getResponseString(EasyMock.isA(HttpEntity.class)))
				.andReturn("response string");
		PowerMock.replay(DefaultOffersUtil.class);

		httpClient.getClientResource(apiUrl, headers,
				new TypeReference<OffersResponse>() {}, responseHeaders);
	}


}
