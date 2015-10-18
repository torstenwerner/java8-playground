package xyz.itsme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class AsyncWebApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AsyncWebApplication.class, args);
    }

    @Autowired
    private GithubConnector connector;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(String... args) throws Exception {
        final CompletableFuture<Map<String, URL>> root = connector.root();
        root.thenAccept(map -> logger.info("user_url: {}", map.get("user_url")));
        root.join();
    }
}
