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
package com.americanexpress.sdk.functional.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import com.americanexpress.sdk.models.default_offers.ErrorMessage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.americanexpress.sdk.exception.DefaultOffersException;

public class DefaultOffersExceptionTest {

	private DefaultOffersException defaultOffersException;

	@Mock
	Throwable cause;

	@Mock
	ErrorMessage error;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testChorusException() {
		defaultOffersException = buildOffersException();
		assertEquals(DefaultOffersException.class, defaultOffersException.getClass());
	}

	@Test
	public void testStatusCodes() {
		defaultOffersException = buildOffersException();
		defaultOffersException.setUserMessage("message");
		assertEquals("message", defaultOffersException.getUserMessage());
	}

	@Test
	public void testExceptionMessages() {
		defaultOffersException = buildOffersException();
		defaultOffersException.setUserMessage("userMessage");
		defaultOffersException.setCause();
		defaultOffersException.setError();
		assertEquals("userMessage", defaultOffersException.getUserMessage());
		assertNotNull(defaultOffersException.getCause());
		assertNotNull(defaultOffersException.getError());
	}

	private DefaultOffersException buildOffersException() {
		defaultOffersException = new DefaultOffersException("message");
		defaultOffersException = new DefaultOffersException("message", cause);
		defaultOffersException = new DefaultOffersException("message", cause, error);

		return defaultOffersException;
	}

}
