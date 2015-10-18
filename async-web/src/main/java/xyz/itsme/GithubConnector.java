package xyz.itsme;

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
        promise.complete(restOperations.getForObject("https://api.github.com/", Map.class));
        return promise;
    }
}
