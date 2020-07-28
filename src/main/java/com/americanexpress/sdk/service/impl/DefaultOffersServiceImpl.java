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

import static com.americanexpress.sdk.service.constants.DefaultOffersApiConstants.*;
import static com.americanexpress.sdk.service.constants.DefaultOffersExceptionConstants.INTERNAL_SDK_EXCEPTION;
import static com.americanexpress.sdk.service.constants.DefaultOffersExceptionConstants.MANDATORY_REQUEST_PARAMETER_ERROR;

import java.util.*;

import javax.ws.rs.core.MultivaluedMap;

import com.americanexpress.sdk.client.core.utils.DefaultOffersUtil;
import com.americanexpress.sdk.exception.*;
import com.americanexpress.sdk.models.default_offers.OffersResponse;
import com.americanexpress.sdk.models.entities.RequestHeader;
import com.americanexpress.sdk.service.DefaultOffersService;

import com.americanexpress.sdk.client.http.HttpClient;
import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.exception.DefaultOffersException;
import com.americanexpress.sdk.exception.DefaultOffersRequestValidationError;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 *
 * The Default Offers Service Implementation class handles all the API operations
 * required for the Default Offers Service
 *
 */
public class DefaultOffersServiceImpl implements DefaultOffersService {

    private final HttpClient httpClient;

    private final Config config;

    public DefaultOffersServiceImpl(final Config config, final HttpClient httpClient) {
        this.config = config;
        this.httpClient = httpClient;
    }

    /**
     * Get the Default Offers for a given entry point
     *
     * @return {@link OffersResponse}
     * @throws DefaultOffersException
     */
    public com.americanexpress.sdk.models.default_offers.OffersResponse getDefaultOffers(String eep, RequestHeader requestHeader) throws DefaultOffersException {

        OffersResponse offersResponse;
        MultivaluedMap<String, Object> headers = DefaultOffersUtil.buildHeaders(requestHeader, config);
        if (null == headers.get(AUTHORIZATION)) {
            throw new DefaultOffersRequestValidationError(MANDATORY_REQUEST_PARAMETER_ERROR);
        }
        try {
            Map<String, String> responseHeaders = new HashMap<>(); //is this needed?
            offersResponse = httpClient.getClientResource(
                    config.getUrl().concat(DEFAULT_OFFERS_GET_RESOURCE_PATH + "?eep=" + eep), headers,
                    new TypeReference<com.americanexpress.sdk.models.default_offers.OffersResponse>() {
                    }, responseHeaders);
        } catch (DefaultOffersResourceNotFoundError ex) {
            throw new NoDefaultOffersFoundError();
        } catch (DefaultOffersException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DefaultOffersException(INTERNAL_SDK_EXCEPTION, ex);
        }
        return offersResponse;
    }


}
