# Default Offers Java SDK
Default Offers API lets you provide American Express standard card offers to third parties in real time.
​

</br></br>This Java implementation allows Amex partners to integrate seamlessly to Default Offers and reduces complexity out of coding service layer integration to Default Offers API. The Default Offers Java SDK is a simple wrapper to the API. It assumes you have already set up your credentials with American Express and have your certs prepared. 
</br></br>

## Table of Contents
 
- [Installation](#installation)
- [Compatibility](#compatibility)
- [Configuration](#configuring-sdk)
- [Authentication](#authentication)
- [Getting Default Offers](#getting-default-offers)
- [Error Handling](#error-handling)
- [Samples](#running-samples)
- [License](#license)
- [Code of Conduct](#code-of-conduct)


<br/>

## Installation

- Install maven 
    ```
    brew install maven
    ```
- Clone repo
- Go inside the repo folder and type
   ```
    $ mvn clean install
   ```
<br/>

## Compatibility

This sdk will support Java Version 8 or higher.



<br/>

## Configuring SDK

The SDK needs to be configured with OAuth, Mutual Auth and Payload encryption configurations. 
Please see the `createDefaultOffersClient()` method in DefaultOffersSample.java for a sample configuration snippet.

```java
private DefaultOffersClient createDefaultOffersClient() throws IOException, CertificateException,
        NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
    Map<String, String> sampleConfig = buildClientConfig();
    DefaultOffersClient defaultOffersClient = null;

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

    defaultOffersClient = DefaultOffersClient.Builder
            .build(Config.builder().url(sampleConfig.get(OAUTH_OFFERS_API_ENDPOINT))
                    .apiKey(sampleConfig.get(OAUTH_API_KEY))
                    .apiSecret(sampleConfig.get(OAUTH_API_SECRET))
                    .accessToken(null)
                    .socketFactory(socketFactory)
                    .proxyConfig(new ProxyConfig(sampleConfig.get(PROXY_PROTOCOL), sampleConfig.get(PROXY_HOST),
                            Integer.valueOf(sampleConfig.get(PROXY_PORT)))).build());

    return defaultOffersClient;
}
```

<br/>

## Authentication

Default Offers API uses token based authentication. The following examples demonstrates how to generate bearer tokens using the SDK :

```java
AccessTokenResponse accessTokenResponse = getAuthenticationToken(defaultOffersClient); //success response

defaultOffersClient.setAccessToken(accessTokenResponse.getAccessToken()); //set the Access Token for further API calls 
```
Sample Response : 

```java
{
  scope: 'default',
  status: 'approved',
  expires_in: '3599', // token expiry in seconds, you can cache the token for the amount of time specified.
  token_type: 'BearerToken',
  access_token: 'wJeW9CPT0DbrqBjrTN1xbMQZkae2'
}
```
Note : you can skip this call if you have an active Token in your cache. if you have an active token, you can just set the bearerToken in config under authentication or call `setAccessToken('access_token')` method to update the config.



<br/>

## Getting Default Offers:

Below is a sample code snippet to get Default Offers. Please refer to DefaultOffersSample.java for the full example. 

```java

DefaultOffersClient defaultOffersClient = createDefaultOffersClient();

DefaultOffersService defaultOffersService = defaultOffersClient.getDefaultOffersService();

RequestHeader requestHeader = objectMapper.readValue(Thread.currentThread()
                                .getContextClassLoader().getResourceAsStream("defaultOffersRequestHeader.json"),
                        RequestHeader.class);

OffersResponse offersResponse = defaultOffersService.getDefaultOffers("entry point", requestHeader);

```
A successful response will return an array of Default Offers.


<br/>

## Error Handling

In case of exceptions encountered while calling American Express APIs, the SDK will throw Errors. 


Possible exceptions : 

- `DefaultOffersAPIError`: Is a generic type of error. It will be raised when there is an Internal server error or any other error which is not covered by any of the named errors.

- `DefaultOffersAuthenticationError`: Authentication errors with the API -- example : invalid API Key or Secret is sent to the API

- `DefaultOffersRequestValidationError`: Request Validation Error -- request or configs provided to the SDK are invalid, you can see more info in err.fields for the fields that failed validations.

- `DefaultOffersesourceNotFoundError`: ResourceNotFoundError will be raised when the the resource is not found.
 
- `NoDefaultOffersFoundError`: NoOffersAvailable will be raised when there are no Default Offers found.




<br/>

## Running Samples 
See DefaultOffersSample.java for a sample usage of Default Offers SDK.

The "sample.properties" resource file contains the variables needed to run the sample.
Please replace the example values with actual values before running the sample.

```java
developer.portal.sdk=jks_file_example.jks           // SDK keystore
keystore.jks=jks                                    // Java keystore format type
oauth.keystore.trust.stream=trust_file_example.jks  // Path to trust store file
oauth.keystore.load.trust.stream=trust_user_example // Keystore username
oauth.keystore.passphrase.property=password_example // Keystore password
oauth.keystore.alias.property=alias_example         // Alias (or name) under which the key is stored in the keystore
oauth.offers.api.endpoint=https://example.aexp.com  // Default Offers API endpoint
oauth.api.key=auth_key_example                      // OAuth Client ID/Key
oauth.api.secret=auth_key_secret_example            // OAuth Secret 
proxy.protocol=http                                 // Protocol Client uses to connect to proxy/load balancer
proxy.host=proxy.example.com                        // Proxy host
proxy.port=8080                                     // Proxy port
```

<br/>


## License

Any contributions made under this project will be governed by the
[Apache License 2.0](./LICENSE.txt).


<br/>

## Code of Conduct

This project adheres to the [American Express Community Guidelines](./CODE_OF_CONDUCT.md). By
participating, you are expected to honor these guidelines.