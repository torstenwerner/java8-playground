package xyz.itsme;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class GithubConnector {
    private final RestOperations restOperations = new RestTemplate();

    public CompletableFuture<Map<String, URL>> root() {
        final CompletableFuture<Map<String, URL>> promise = new CompletableFuture<>();
        promise.complete(callGithub().getBody());
        return promise;
    }

    private final ParameterizedTypeReference<Map<String, URL>> responseType =
            new ParameterizedTypeReference<Map<String, URL>>() {
            };

    private ResponseEntity<Map<String, URL>> callGithub() {
        return restOperations.exchange("https://api.github.com/", HttpMethod.GET, null, responseType);
    }
}
