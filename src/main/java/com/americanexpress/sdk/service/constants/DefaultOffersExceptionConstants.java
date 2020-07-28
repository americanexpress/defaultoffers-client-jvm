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
package com.americanexpress.sdk.service.constants;

import lombok.experimental.UtilityClass;

/**
 * This class holds the Constants of Defaults Offers Service Exception descriptions
 * and details
 *
 * @author jramio
 */
@UtilityClass
public class DefaultOffersExceptionConstants {

	/** services error constants */
	public static final String OFFERS_REQUEST_VALIDATION_ERROR = "Request validation failed";
	public static final String MANDATORY_REQUEST_PARAMETER_ERROR = "Mandatory request parameters missing";

	/**
	 * Error Messages
	 */
	public static final String INTERNAL_SDK_EXCEPTION = "Internal SDK Exception";
	public static final String INTERNAL_API_EXCEPTION = "Internal API Exception";

}
