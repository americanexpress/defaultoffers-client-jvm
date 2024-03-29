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
package com.americanexpress.sdk.functional.service.impl;

import static org.junit.Assert.assertNotNull;

import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.exception.DefaultOffersException;
import com.americanexpress.sdk.exception.DefaultOffersRequestValidationError;
import com.americanexpress.sdk.service.DefaultOffersClient;
import com.americanexpress.sdk.service.impl.AuthenticationServiceImpl;
import org.apache.http.HttpEntity;
import org.easymock.classextension.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.americanexpress.sdk.client.http.ApiClientFactory;
import com.americanexpress.sdk.client.http.HttpClient;
import com.americanexpress.sdk.models.entities.AccessTokenResponse;
import com.americanexpress.sdk.service.AuthenticationService;
import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AccessTokenResponse.class, AuthenticationService.class, DefaultOffersClient.class,
		ApiClientFactory.class })

public class AuthenticationServiceImplTest {

	HttpClient httpClient;
	Config config;
	AuthenticationService authenticationService;

	@Test
	public void testGetAccessToken() throws DefaultOffersException {
		httpClient = EasyMock.createNiceMock(HttpClient.class);
		config = Config.builder().apiKey("apiKey").apiSecret("apiSecret")
				.url("https://example.americanexpress.com").build();
		authenticationService = new AuthenticationServiceImpl(config, httpClient);
		AccessTokenResponse accessTokenResponse = new AccessTokenResponse();

		EasyMock.expect(httpClient.postClientResource(EasyMock.isA(HttpEntity.class), EasyMock.isA(String.class),
				EasyMock.anyObject(), (TypeReference<Object>) EasyMock.isA(Object.class), EasyMock.anyObject()))
				.andReturn(accessTokenResponse);
		EasyMock.replay(httpClient);
		AccessTokenResponse response = authenticationService.getAccessToken();
		assertNotNull(response);
	}

	@Test (expected = DefaultOffersRequestValidationError.class)
	public void testGetAccessToken_InvalidConfig() throws DefaultOffersException {
		httpClient = EasyMock.createNiceMock(HttpClient.class);
		config = Config.builder().build();
		authenticationService= new AuthenticationServiceImpl(config, httpClient);
		authenticationService.getAccessToken();
	}
}
