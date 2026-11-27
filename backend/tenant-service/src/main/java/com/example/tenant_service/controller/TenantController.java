
package com.example.tenant_service.controller;

import com.example.tenant_service.dto.TenantRequest;
import com.example.tenant_service.dto.TenantResponse;
import com.example.tenant_service.service.TenantService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@CrossOrigin(origins = "*")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    // Lay danh sach tat ca nguoi thue
    @GetMapping
    public ResponseEntity<List<TenantResponse>> getAllTenants() {
        return ResponseEntity.ok(
                tenantService.getAllTenants()
        );
    }

    // Tim nguoi thue theo ID
    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> getTenantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                tenantService.getTenantById(id)
        );
    }

    // Them nguoi thue moi
    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(
            @Valid @RequestBody TenantRequest request) {

        TenantResponse response =
                tenantService.createTenant(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // Cap nhat thong tin nguoi thue
    @PutMapping("/{id}")
    public ResponseEntity<TenantResponse> updateTenant(
            @PathVariable Long id,
            @Valid @RequestBody TenantRequest request) {

        return ResponseEntity.ok(
                tenantService.updateTenant(id, request)
        );
    }

    // Xoa nguoi thue
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(
            @PathVariable Long id) {

        tenantService.deleteTenant(id);

        return ResponseEntity.noContent().build();
    }
}
