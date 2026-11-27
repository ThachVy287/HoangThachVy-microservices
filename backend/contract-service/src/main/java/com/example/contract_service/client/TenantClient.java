
package com.example.contract_service.client;

import com.example.contract_service.dto.TenantResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TenantClient {

    private final RestClient tenantRestClient;

    public TenantClient(
            @Qualifier("tenantRestClient") RestClient tenantRestClient) {
        this.tenantRestClient = tenantRestClient;
    }

    // Lay thong tin nguoi thue theo ID tu Tenant Service
    public TenantResponse getTenantById(Long tenantId) {

        return tenantRestClient.get()
                .uri("/api/tenants/{id}", tenantId)
                .retrieve()
                .body(TenantResponse.class);
    }
}
