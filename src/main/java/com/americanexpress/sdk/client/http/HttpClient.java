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
package com.americanexpress.sdk.client.http;

import static com.americanexpress.sdk.service.constants.DefaultOffersExceptionConstants.INTERNAL_API_EXCEPTION;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.americanexpress.sdk.client.core.utils.DefaultOffersUtil;
import com.americanexpress.sdk.exception.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import com.americanexpress.sdk.exception.DefaultOffersException;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The HttpClient class handles all the HTTP operations for Default Offers SDK
 * 
 * @author jramio
 */
public class HttpClient {

	/**
	 * Client interface to connect to external service
	 */
	CloseableHttpClient client;

	/**
	 * @param client
	 */
	public HttpClient(CloseableHttpClient client) {
		this.client = client;
	}

	/**
	 * This method executes a POST request to an an API
	 *
	 * @param apiRequest
	 * @param apiUrl
	 * @param headers
	 * @param responseObject
	 * @param responseHeaders
	 * @return <R, T> R
	 * @throws DefaultOffersException
	 */
	public <R, T> R postClientResource(T apiRequest, String apiUrl, MultivaluedMap<String, Object> headers,
			TypeReference<R> responseObject, Map<String, String> responseHeaders) throws DefaultOffersException {
		R response = null;
		HttpPost request = new HttpPost(apiUrl);
		HttpEntity entity = (HttpEntity) apiRequest;
		request.setEntity(entity);
		addHeaders(request, headers);
		try (CloseableHttpResponse httpResponse = client.execute(request)) {
			if (null != httpResponse.getEntity() && (httpResponse.getStatusLine().getStatusCode() == Response.Status.OK
					.getStatusCode()
					|| httpResponse.getStatusLine().getStatusCode() == Response.Status.CREATED.getStatusCode())) {
				if (null != responseObject) {
					response = DefaultOffersUtil.generateResponse(responseObject, httpResponse);
				}
				if (null != responseHeaders) {
					extractResponseHeaders(httpResponse, responseHeaders);
				}
				return response;
			} else {
				throw handleHttpStatusCodes(httpResponse);
			}
		} catch (DefaultOffersException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new DefaultOffersApiError(INTERNAL_API_EXCEPTION, ex);
		}
	}

	/**
	 * This method executes a GET request to an an API
	 *
	 * @param apiUrl
	 * @param headers
	 * @param responseObject
	 * @param responseHeaders
	 * @return <R> R
	 * @throws DefaultOffersException
	 */
	public <R> R getClientResource(String apiUrl, MultivaluedMap<String, Object> headers,
									   TypeReference<R> responseObject, Map<String, String> responseHeaders)
			throws DefaultOffersException {
		R response = null;
		HttpGet getRequest = new HttpGet(apiUrl);
		addHeaders(getRequest, headers);
		try (CloseableHttpResponse httpResponse = client.execute(getRequest)) {
			if (null != httpResponse.getEntity() && (httpResponse.getStatusLine().getStatusCode() == Response.Status.OK
					.getStatusCode()
					|| httpResponse.getStatusLine().getStatusCode() == Response.Status.CREATED.getStatusCode())) {
				if (null != responseObject) {
					response = DefaultOffersUtil.generateResponse(responseObject, httpResponse);
				}
				if (null != responseHeaders) {
					extractResponseHeaders(httpResponse, responseHeaders);
				}
				return response;
			} else {
				throw handleHttpStatusCodes(httpResponse);
			}
		} catch (DefaultOffersException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new DefaultOffersApiError(INTERNAL_API_EXCEPTION, ex);
		}
	}

	/**
	 * This method extracts the response headers
	 *
	 * @param httpResponse
	 * @param responseHeaders
	 */
	private void extractResponseHeaders(CloseableHttpResponse httpResponse, Map<String, String> responseHeaders) {
		Header[] headers = httpResponse.getAllHeaders();
		for (Header header : headers) {
			responseHeaders.put(header.getName(), header.getValue());
		}
	}

	/**
	 * This method adds required headers to the request
	 *
	 * @param request
	 * @param headers
	 */
	private void addHeaders(HttpRequest request, MultivaluedMap<String, Object> headers) {
		for (String str : headers.keySet()) {
			request.addHeader(str, String.valueOf(headers.getFirst(str)));
		}
	}

	/**
	 * This method provides proper exception handling
	 *
	 * @param httpResponse
	 */
	private DefaultOffersException handleHttpStatusCodes(CloseableHttpResponse httpResponse) {
		String developerMessage = DefaultOffersUtil.getResponseString(httpResponse.getEntity());
		Response.Status status = Response.Status.fromStatusCode(httpResponse.getStatusLine().getStatusCode());
		switch (status) {
		case NOT_FOUND:
			return new DefaultOffersResourceNotFoundError();
		case BAD_REQUEST:
			return new DefaultOffersRequestValidationError(developerMessage);
		case UNAUTHORIZED:
			return new DefaultOffersAuthenticationError(developerMessage);
		default:
			return new DefaultOffersApiError(developerMessage);
		}
	}
}
