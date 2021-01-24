package com.nerd.PaymentProcessApi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfiguration {

    public RestTemplate restTemplate(HttpProperties properties) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory((
            properties.getProtocol() + "://" +
            properties.getHost() + ":" +
            properties.getPort() +
            properties.getBasePath()
        )));
        return restTemplate;
    }

    @Bean
    @ConfigurationProperties(prefix = "providers.loan-system-api")
    public HttpProperties loanSystemApiProperties() { return new HttpProperties(); }
}
