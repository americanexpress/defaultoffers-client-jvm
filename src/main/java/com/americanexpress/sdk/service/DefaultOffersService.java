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
package com.americanexpress.sdk.service;

import com.americanexpress.sdk.client.http.HttpClient;
import com.americanexpress.sdk.configuration.Config;
import com.americanexpress.sdk.exception.DefaultOffersException;
import com.americanexpress.sdk.models.default_offers.OffersResponse;
import com.americanexpress.sdk.models.entities.RequestHeader;
import com.americanexpress.sdk.service.impl.DefaultOffersServiceImpl;

/**
 *
 * The Default Offer Service Interface handles all the API operations required for
 * Default Offers service
 *
 * @author jsheu
 */
public interface DefaultOffersService {
    /**
     * Get Default Offers from Default Offers API
     *
     * @return {@link OffersResponse}
     * @throws DefaultOffersException
     */
    OffersResponse getDefaultOffers(String eep, RequestHeader requestHeader) throws DefaultOffersException;

    class Builder {
        public static DefaultOffersService create(final Config config, final HttpClient authClient) {
            return new DefaultOffersServiceImpl(config, authClient);
        }

        private Builder() {

        }
    }
}
