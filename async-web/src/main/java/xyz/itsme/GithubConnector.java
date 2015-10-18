package xyz.itsme;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class GithubConnector {
    private final AsyncRestOperations restOperations;

    public GithubConnector() {
        // set timeouts in milliseconds
        final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(10000)
                .setConnectTimeout(10000)
                .setSocketTimeout(60000)
                .build();

        // set request configuration and connection pool size
        final CloseableHttpAsyncClient asyncClient = HttpAsyncClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(10)
                .setMaxConnTotal(30)
                .build();

        final AsyncClientHttpRequestFactory requestFactory = new HttpComponentsAsyncClientHttpRequestFactory(asyncClient);
        restOperations = new AsyncRestTemplate(requestFactory);
    }

    public CompletableFuture<Map<String, URL>> root() {
        final CompletableFuture<Map<String, URL>> promise = new CompletableFuture<>();
        callGithub().addCallback(response -> promise.complete(response.getBody()), promise::completeExceptionally);
        return promise;
    }

    private final ParameterizedTypeReference<Map<String, URL>> responseType =
            new ParameterizedTypeReference<Map<String, URL>>() {
            };

    private ListenableFuture<ResponseEntity<Map<String, URL>>> callGithub() {
        return restOperations.exchange("https://api.github.com/", HttpMethod.GET, null, responseType);
    }
}
