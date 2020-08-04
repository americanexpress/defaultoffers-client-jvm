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
package com.americanexpress.sdk.functional.sample;

import com.americanexpress.sdk.exception.DefaultOffersException;
import com.americanexpress.sdk.models.default_offers.OffersResponse;
import com.americanexpress.sdk.models.entities.*;
import com.americanexpress.sdk.service.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;

import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.configuration.ProxyConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.americanexpress.sdk.service.constants.DefaultOffersApiConstants.*;

public class DefaultOffersSample {
    DefaultOffersClient defaultOffersClient;
    DefaultOffersService defaultOffersService;
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        defaultOffersClient = createDefaultOffersClient();
        defaultOffersService = defaultOffersClient.getDefaultOffersService();
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    /**
     * Sample for getting Default Offers
     *
     */
    @Test
    public void getDefaultOffers () {
        try {
            /**
             * If a valid access Token is already available in the cache, please build the
             * configuration with the available token. getting Authentication Token call is
             * not needed.
             */
            AccessTokenResponse accessTokenResponse = getAuthenticationToken(defaultOffersClient);
            if (null != accessTokenResponse && StringUtils.isNotEmpty(accessTokenResponse.getAccessToken())) {
                defaultOffersClient.setAccessToken(accessTokenResponse.getAccessToken());
                System.out.println("AccessToken: " + accessTokenResponse.getAccessToken());
                /**
                 * Populate the request header
                 */
                RequestHeader requestHeader = objectMapper.readValue(Thread.currentThread()
                                .getContextClassLoader().getResourceAsStream("defaultOffersRequestHeader.json"),
                        RequestHeader.class);
                /**
                 * send GET request to Default Offers API.
                 * The External Entry Point (EEP) will determine which default offer is returned.
                 */
                OffersResponse offersResponse = defaultOffersService.getDefaultOffers("defaultoffers", requestHeader);
                System.out.println("Default Offer: " + offersResponse.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Initializes the HTTP Client instance
     *
     * @throws IOException
     */
    private Map<String, String> buildClientConfig() throws IOException {
        Properties defaultOffersSampleConfiguration = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sample.properties");
        defaultOffersSampleConfiguration.load(inputStream);
        return new HashMap(defaultOffersSampleConfiguration);
    }



    /**
     * Initialize the Default Offers Client
     *
     * @return DefaultOffersClient
     * @throws DefaultOffersException
     * @throws IOException
     */
    private DefaultOffersClient createDefaultOffersClient() throws IOException, CertificateException,
            NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        Map<String, String> sampleConfig = buildClientConfig();

        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(sampleConfig.get(DEVELOPER_PORTAL_SDK));
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(inputStream, sampleConfig.get(OAUTH_KEYSTORE_PASSPHRASE_PROPERTY).toCharArray());
        KeyStore trustStore = KeyStore.getInstance(sampleConfig.get(KEYSTORE_JKS));
        InputStream trustStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(sampleConfig.get(OAUTH_KEYSTORE_TRUST_STREAM));
        trustStore.load(trustStream, sampleConfig.get(OAUTH_KEYSTORE_LOAD_TRUST_STREAM).toCharArray());

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                new SSLContextBuilder().loadTrustMaterial(trustStore, (chain, authType) -> false)
                        .loadKeyMaterial(ks, sampleConfig.get(OAUTH_KEYSTORE_PASSPHRASE_PROPERTY).toCharArray(),
                                (aliases, socket) -> sampleConfig.get(OAUTH_KEYSTORE_ALIAS_PROPERTY))
                        .build());

        return DefaultOffersClient.Builder
                .build(Config.builder().url(sampleConfig.get(OAUTH_OFFERS_API_ENDPOINT))
                        .apiKey(sampleConfig.get(OAUTH_API_KEY))
                        .apiSecret(sampleConfig.get(OAUTH_API_SECRET))
                        .accessToken(null)
                        .socketFactory(socketFactory)
                        .proxyConfig(new ProxyConfig(sampleConfig.get(PROXY_PROTOCOL), sampleConfig.get(PROXY_HOST),
                                Integer.valueOf(sampleConfig.get(PROXY_PORT)))).build());
    }

    /**
     * This method will get the access Token from the SDK.
     *
     * @param defaultOffersClient
     * @return AccessTokenResponse
     * @throws DefaultOffersException
     */
    private AccessTokenResponse getAuthenticationToken(DefaultOffersClient defaultOffersClient)
            throws DefaultOffersException {
        AccessTokenResponse accessTokenResponse = null;
        AuthenticationService authenticationService = defaultOffersClient.getAuthenticationService();
        accessTokenResponse = authenticationService.getAccessToken();
        return accessTokenResponse;
    }
}
