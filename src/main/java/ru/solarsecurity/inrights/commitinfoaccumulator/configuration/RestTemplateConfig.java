package ru.solarsecurity.inrights.commitinfoaccumulator.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final ApplicationConfig config;

    @Bean("gitlabRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .defaultHeader("Authorization", "Bearer " + config.gitlab().token())
                .uriTemplateHandler(new DefaultUriBuilderFactory(config.gitlab().url()))
                .build();
    }
}
