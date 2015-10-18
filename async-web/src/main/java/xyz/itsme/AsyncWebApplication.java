package xyz.itsme;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

@SpringBootApplication
public class AsyncWebApplication {
    @Bean
    public AsyncRestOperations restOperations() {
        // set timeouts in milliseconds
        final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(10000)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();

        // set request configuration and connection pool size
        final CloseableHttpAsyncClient asyncClient = HttpAsyncClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(10)
                .setMaxConnTotal(30)
                .build();

        final AsyncClientHttpRequestFactory requestFactory = new HttpComponentsAsyncClientHttpRequestFactory(asyncClient);
        return new AsyncRestTemplate(requestFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(AsyncWebApplication.class, args);
    }
}
