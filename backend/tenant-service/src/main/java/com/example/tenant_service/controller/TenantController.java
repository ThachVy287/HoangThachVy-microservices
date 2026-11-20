
package com.example.tenant_service.controller;

import com.example.tenant_service.entity.Tenant;
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

    // API lay danh sach nguoi thue
    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenants() {
        return ResponseEntity.ok(
                tenantService.getAllTenants()
        );
    }

    // API tim nguoi thue theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                tenantService.getTenantById(id)
        );
    }

    // API them nguoi thue
    @PostMapping
    public ResponseEntity<Tenant> createTenant(
            @Valid @RequestBody Tenant tenant) {

        Tenant newTenant = tenantService.createTenant(tenant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newTenant);
    }

    // API cap nhat nguoi thue
    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(
            @PathVariable Long id,
            @Valid @RequestBody Tenant tenant) {

        return ResponseEntity.ok(
                tenantService.updateTenant(id, tenant)
        );
    }

    // API xoa nguoi thue
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(
            @PathVariable Long id) {

        tenantService.deleteTenant(id);

        return ResponseEntity.noContent().build();
    }
}
