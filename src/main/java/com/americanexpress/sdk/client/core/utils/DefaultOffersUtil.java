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
package com.americanexpress.sdk.client.core.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.models.entities.RequestHeader;
import com.americanexpress.sdk.service.constants.DefaultOffersApiConstants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * The DefaultOffersUtil class contains utility methods for Default Offers SDK
 * 
 * @author jramio
 */
@UtilityClass
public class DefaultOffersUtil {
	/**
	 * This method is the builder for Default offers request Headers
	 * 
	 * @param requestHeader
	 * @param config
	 * @return MultivaluedMap<String, Object>
	 */
	public static MultivaluedMap<String, Object> buildHeaders(RequestHeader requestHeader, Config config) {
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.add(DefaultOffersApiConstants.REQUEST_HEADER_REQUEST_ID, requestHeader.getRequestId());
		headers.add(DefaultOffersApiConstants.REQUEST_HEADER_CLIENT_ID, requestHeader.getClientId());
		headers.add(DefaultOffersApiConstants.REQUEST_HEADER_MESSAGE_TYPE_ID, requestHeader.getMessageTypeId());
		headers.add(DefaultOffersApiConstants.REQUEST_HEADER_CONTENT_TYPE, DefaultOffersApiConstants.REQUEST_HEADER_APPLICATION_JSON);
		headers.add(DefaultOffersApiConstants.MAC_ID, config.getApiKey());
		headers.add(DefaultOffersApiConstants.AUTHORIZATION, DefaultOffersApiConstants.BEARER + " " + config.getAccessToken());
		return headers;
	}

	/**
	 * This method gets the response data string
	 * 
	 * @param entity
	 * @return String
	 */
	public static String getResponseString(HttpEntity entity) {
		if (entity != null) {
			try {
				return EntityUtils.toString(entity);
			} catch (Exception ex) {
				return null;
			}
		}
		return null;
	}

	/**
	 * This method converts response based on content-type
	 *
	 * @param responseObject
	 * @param httpResponse
	 * @return <R> R
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	public static <R> R generateResponse(TypeReference<R> responseObject, CloseableHttpResponse httpResponse)
			throws IOException {
		final InputStream content = httpResponse.getEntity().getContent();
		ObjectMapper mapperForGetResponse = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapperForGetResponse.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		return mapperForGetResponse.readValue(content, responseObject);
	}
}
