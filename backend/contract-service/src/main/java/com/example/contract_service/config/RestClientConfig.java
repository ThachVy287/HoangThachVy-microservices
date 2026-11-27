
package com.example.contract_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    // Ket noi den Room Service
    @Bean
    @Qualifier("roomRestClient")
    public RestClient roomRestClient(
            @Value("${services.room.url}") String roomUrl) {

        return RestClient.builder()
                .baseUrl(roomUrl)
                .build();
    }

    // Ket noi den Tenant Service
    @Bean
    @Qualifier("tenantRestClient")
    public RestClient tenantRestClient(
            @Value("${services.tenant.url}") String tenantUrl) {

        return RestClient.builder()
                .baseUrl(tenantUrl)
                .build();
    }
}
