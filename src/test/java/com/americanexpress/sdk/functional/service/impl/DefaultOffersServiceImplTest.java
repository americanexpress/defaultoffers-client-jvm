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

import java.util.Map;

import com.americanexpress.sdk.exception.DefaultOffersException;
import com.americanexpress.sdk.models.default_offers.OffersResponse;
import com.americanexpress.sdk.service.DefaultOffersService;
import com.americanexpress.sdk.service.impl.DefaultOffersServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.americanexpress.sdk.client.http.HttpClient;
import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.models.entities.RequestHeader;

import javax.ws.rs.core.MultivaluedMap;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class DefaultOffersServiceImplTest {

	private HttpClient httpClient;
	private DefaultOffersService defaultOffersService;
	private RequestHeader requestHeader;
	private OffersResponse offersResponse;

	@Before
	public void setUp() {
		Config config = Config.builder().accessToken("accessToken")
				.url("https://example.americanexpress.com").build();
		httpClient = EasyMock.createNiceMock(HttpClient.class);
		defaultOffersService = new DefaultOffersServiceImpl(config, httpClient);
		requestHeader = RequestHeader.builder().build();
		offersResponse = new OffersResponse();
	}

	@Test
	public void testGetDefaultOffers() throws DefaultOffersException {
		EasyMock.expect(httpClient.getClientResource(
				EasyMock.isA(String.class),
				EasyMock.isA(MultivaluedMap.class),
				(TypeReference<Object>) EasyMock.isA(Object.class),
				EasyMock.isA(Map.class)))
				.andReturn(offersResponse);
		EasyMock.replay(httpClient);

		OffersResponse result = defaultOffersService.getDefaultOffers("eep", requestHeader);
		assertNotNull(result);
	}

}
