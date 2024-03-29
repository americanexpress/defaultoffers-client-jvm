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
package com.americanexpress.sdk.exception;


import com.americanexpress.sdk.models.default_offers.ErrorMessage;

/**
 * The DefaultOffersException class handles how exceptions are thrown
 *
 */

public class DefaultOffersException extends Exception {


	/**
	 * message describing the error
	 */
	String userMessage;

	/**
	 * detailed error description for developers
	 */
	String developerMessage;
	/**
	 * throwable cause
	 */
	Throwable cause;
	/**
	 * generic error
	 */
	private transient ErrorMessage error;

	/**
	 * @param message
	 * @param developerMessage
	 */
	public DefaultOffersException(String message, String developerMessage) {
		this.userMessage = message;
		this.developerMessage = developerMessage;
	}

	/**
	 * @param message
	 */
	public DefaultOffersException(String message) {
		this.userMessage = message;
	}

	/**
	 * @param userMessage
	 * @param developerMessage
	 * @param cause
	 */
	public DefaultOffersException(String userMessage, String developerMessage, Throwable cause) {
		this.userMessage = userMessage;
		this.developerMessage = developerMessage;
		this.cause = cause;
	}

	/**
	 * @param error
	 * @param cause
	 */
	public DefaultOffersException(String message, Throwable cause, ErrorMessage error) {
		this.userMessage = message;
		this.error = error;
		this.cause = cause;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DefaultOffersException(String message, Throwable cause) {
		this.userMessage = message;
		this.cause = cause;
	}

	public ErrorMessage getError() {
		return error;
	}

	public ErrorMessage setError() {
		return error;
	}

	@Override
	public synchronized Throwable getCause() {
		return cause;
	}

	public Throwable setCause() {
		return cause;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
}
