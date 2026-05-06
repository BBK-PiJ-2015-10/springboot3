package com.springboot3.example2.youtube;

import com.springboot3.example2.youtube.services.YouTube;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class YouTubeConfiguration {

    public static String YOUTUBE_V3_API = "https://www.googleapis.com/youtube/v3";

    @Bean
    WebClient youTubeApiClient(OAuth2AuthorizedClientManager clientManager) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oath2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientManager);

        oath2.setDefaultClientRegistrationId("google");

        return WebClient.builder().baseUrl(YOUTUBE_V3_API).apply(oath2.oauth2Configuration()).build();
    }

    @Bean
    YouTube youTube(WebClient youTubeApiClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(youTubeApiClient)).build();
        return factory.createClient(YouTube.class);
    }

}
