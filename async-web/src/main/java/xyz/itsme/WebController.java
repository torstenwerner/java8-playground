package xyz.itsme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.URL;
import java.util.Map;
import java.util.Objects;

@RestController
public class WebController {
    private final GithubConnector connector;

    @Autowired
    public WebController(GithubConnector connector) {
        this.connector = Objects.requireNonNull(connector);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public DeferredResult<Map<String, URL>> root() {
        final DeferredResult<Map<String, URL>> deferredResult = new DeferredResult<>(60000);
        connector.root()
                .thenApply(deferredResult::setResult)
                .exceptionally(deferredResult::setErrorResult);
        return deferredResult;
    }
}
