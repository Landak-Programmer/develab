package com.landak.develab.http;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import com.google.gson.Gson;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(
        value = "app.integration.http-service",
        havingValue = "true")
@Service
public class HttpServiceImpl implements HttpService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Gson gson;

    private final RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
            .handle(HttpHostConnectException.class)
            .withDelay(Duration.ofSeconds(10))
            .withMaxRetries(1)
            .onRetry(objectExecutionAttemptedEvent -> {
                if (logger.isWarnEnabled()) {
                    logger.warn(
                            "Retry #{} due to: {}",
                            objectExecutionAttemptedEvent.getAttemptCount(),
                            objectExecutionAttemptedEvent
                                    .getLastFailure()
                                    .getLocalizedMessage());
                }
            });

    @Override
    public boolean execute(final HttpRequest request) {
        return execute(request, null);
    }

    @Override
    public <R> R execute(final HttpRequest request, final Class<R> rClazz) {
        return Failsafe
                .with(retryPolicy)
                .get(() -> {
                    final HttpRequestBase requestBase = convertToLegacyRequest(request);
                    final CloseableHttpClient client = this.buildCloseableHttpClient(
                            this.buildSocketFactory(this.buildSSLContextBuilder()), this.buildRequestConfig());

                    final HttpResponse response = client.execute(requestBase);
                    final HttpEntity entity = response.getEntity();
                    final StatusLine requestLine = response.getStatusLine();
                    if (!isSuccessful(requestLine.getStatusCode())) {
                        logger.error("Request for operation {} is not successful. Url {}, Code {} ,Message {}",
                                request.getMethod(), request.getUrl(), requestLine.getStatusCode(), requestLine.getReasonPhrase());
                        return processBooleanResponse(false);
                    }
                    final String responseBody = EntityUtils.toString(entity);
                    if (rClazz != null) {
                        return gson.fromJson(responseBody, rClazz);
                    } else {
                        return processBooleanResponse(true);
                    }
                });
    }

    private <R> R processBooleanResponse(boolean isSuccessful) {
        return (R) gson.fromJson(String.valueOf(isSuccessful), Boolean.class);
    }

    private HttpRequestBase convertToLegacyRequest(final HttpRequest request) throws UnsupportedEncodingException {
        final HttpRequestBase req;

        if (HttpMethod.GET.equals(request.getMethod())) {
            req = new HttpGet(request.getUrl());
        } else if (HttpMethod.POST.equals(request.getMethod())) {
            req = new HttpPost(request.getUrl());
        } else if (HttpMethod.PUT.equals(request.getMethod())) {
            req = new HttpPut(request.getUrl());
        } else if (HttpMethod.DELETE.equals(request.getMethod())) {
            req = new HttpDelete(request.getUrl());
        } else {
            throw new UnsupportedOperationException(String.format("No such http method for %s", request.getMethod()));
        }
        request
                .getHeader()
                .forEach(req::addHeader);
        if (request.getBody() != null && req instanceof HttpEntityEnclosingRequestBase) {
            ((HttpEntityEnclosingRequestBase) req)
                    .setEntity(new StringEntity(gson.toJson(request.getBody())));
        }
        return req;
    }

    private RequestConfig buildRequestConfig() {
        return RequestConfig
                .custom()
                .setConnectTimeout(60 * 1000)
                .setConnectionRequestTimeout(120 * 1000)
                .build();
    }

    private SSLContextBuilder buildSSLContextBuilder() throws NoSuchAlgorithmException, KeyStoreException {
        final SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        return builder;
    }

    private SSLConnectionSocketFactory buildSocketFactory(final SSLContextBuilder builder) throws NoSuchAlgorithmException, KeyManagementException {
        return new SSLConnectionSocketFactory(builder.build());
    }

    private CloseableHttpClient buildCloseableHttpClient(final SSLConnectionSocketFactory sslsf, final RequestConfig requestConfig) {
        return HttpClients
                .custom()
                .setSSLSocketFactory(
                        sslsf)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    private boolean isSuccessful(int code) {
        return code >= 200 && code < 300;
    }

}
