package xyz.itsme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class WebController {
    private final GithubConnector connector;

    @Autowired
    public WebController(GithubConnector connector) {
        this.connector = connector;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, URL> root() throws ExecutionException, InterruptedException {
        final CompletableFuture<Map<String, URL>> root = connector.root();
        return root.get();
    }
}
