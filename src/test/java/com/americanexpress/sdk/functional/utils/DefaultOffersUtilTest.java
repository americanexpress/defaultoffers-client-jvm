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
package com.americanexpress.sdk.functional.utils;

import static org.junit.Assert.assertNotNull;


import com.americanexpress.sdk.client.http.ApiClientFactory;
import com.nimbusds.jose.JWEHeader;
import org.apache.http.HttpEntity;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.americanexpress.sdk.client.core.utils.DefaultOffersUtil;
import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.models.entities.RequestHeader;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpEntity.class, ObjectMapper.class, ApiClientFactory.class, JWEHeader.class})
public class DefaultOffersUtilTest {


	@Test
	public void testBuildHeaders() {
		Config config = org.easymock.EasyMock.createMock(Config.class);
		org.easymock.EasyMock.expect(config.getApiKey()).andReturn("api_key");
		org.easymock.EasyMock.expect(config.getAccessToken()).andReturn("access_token");
		EasyMock.replay(config);
		assertNotNull(DefaultOffersUtil.buildHeaders(buildRequestHeader(), config));
	}


	private RequestHeader buildRequestHeader() {
		RequestHeader requestHeader = new RequestHeader();
		requestHeader.setRequestId("request id");
		requestHeader.setClientId("client id");
		requestHeader.setMessageTypeId("message type id");
		return requestHeader;
	}
}
