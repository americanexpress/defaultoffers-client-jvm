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
package com.americanexpress.sdk.service.impl;

import com.americanexpress.sdk.client.http.ApiClientFactory;
import com.americanexpress.sdk.client.http.HttpClient;
import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.service.*;

/**
 *
 * The Default Offers Client Implementation class is used to hand all Default Offers service actions
 *
 */
public class DefaultOffersClientImpl implements DefaultOffersClient {

    /**
     * Authentication service
     */
    private AuthenticationService authenticationService;

    /**
     * Default Offers service
     */
    private DefaultOffersService defaultOffersService;

    /**
     * HTTP Client
     */
    private HttpClient httpClient;

    /**
     * configuration
     */
    private Config config;

    /**
     * constructor for Default Offers client implementation
     *
     * @param config
     */
    public DefaultOffersClientImpl(Config config) {
        this.config = config;
        httpClient = ApiClientFactory.createHttpClient(config);
    }

    public void setAccessToken(String accessToken) {
        this.config.setAccessToken(accessToken);
    }

    /**
     * create Authentication Service
     */
    public AuthenticationService getAuthenticationService() {

        if (authenticationService == null) {
            authenticationService = AuthenticationService.Builder.create(config, httpClient);
        }
        return authenticationService;
    }


    /**
     * create Default Offers Service
     */
    public DefaultOffersService getDefaultOffersService() {

        if (defaultOffersService == null) {
            defaultOffersService = DefaultOffersService.Builder.create(config, httpClient);
        }
        return defaultOffersService;
    }

}
