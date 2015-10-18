package xyz.itsme;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class GithubConnector {
    private final AsyncRestOperations restOperations = new AsyncRestTemplate();

    public CompletableFuture<Map<String, URL>> root() {
        final CompletableFuture<Map<String, URL>> promise = new CompletableFuture<>();
        callGithub().addCallback(result -> promise.complete(result.getBody()), promise::completeExceptionally);
        return promise;
    }

    private final ParameterizedTypeReference<Map<String, URL>> responseType =
            new ParameterizedTypeReference<Map<String, URL>>() {
            };

    private ListenableFuture<ResponseEntity<Map<String, URL>>> callGithub() {
        return restOperations.exchange("https://api.github.com/", HttpMethod.GET, null, responseType);
    }
}
