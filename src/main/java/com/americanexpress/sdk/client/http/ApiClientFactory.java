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

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.service.constants.DefaultOffersApiConstants;

/**
 * The ApiClientFactory class creates the Http Client
 *
 */
@UtilityClass
public class ApiClientFactory {

	/**
	 * This method will be used create HTTP client instance
	 * 
	 * @param config
	 * @return HttpClient
	 */
	public static HttpClient createHttpClient(Config config) {
		HttpHost proxy = null;
		SSLConnectionSocketFactory socketFactory = null;
		if (null != config.getSocketFactory()) {
			socketFactory = config.getSocketFactory();
		}
		if (null != config.getProxyConfig()) {
			proxy = new HttpHost(config.getProxyConfig().getHost(), config.getProxyConfig().getPort(),
					StringUtils.isEmpty(config.getProxyConfig().getProtocol()) ? DefaultOffersApiConstants.API_CLIENT_TYPE_HTTP
							: config.getProxyConfig().getProtocol());
		}
		CloseableHttpClient client = HttpClients.custom().setProxy(proxy).setSSLSocketFactory(socketFactory).build();
		return new HttpClient(client);
	}

}
