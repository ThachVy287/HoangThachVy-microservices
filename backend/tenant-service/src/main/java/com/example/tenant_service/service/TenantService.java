
package com.example.tenant_service.service;

import com.example.tenant_service.entity.Tenant;
import com.example.tenant_service.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    // Lay danh sach tat ca nguoi thue
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    // Tim nguoi thue theo ID
    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Khong tim thay nguoi thue co ID: " + id
                        ));
    }

    // Them nguoi thue moi
    public Tenant createTenant(Tenant tenant) {

        if (tenantRepository.existsByPhone(tenant.getPhone())) {
            throw new RuntimeException(
                    "So dien thoai " + tenant.getPhone()
                    + " da duoc su dung!"
            );
        }

        if (tenantRepository.existsByIdentityNumber(
                tenant.getIdentityNumber())) {
            throw new RuntimeException(
                    "So CCCD " + tenant.getIdentityNumber()
                    + " da ton tai!"
            );
        }

        return tenantRepository.save(tenant);
    }

    // Cap nhat thong tin nguoi thue
    public Tenant updateTenant(Long id, Tenant tenantDetails) {

        Tenant tenant = getTenantById(id);

        if (!tenant.getPhone().equals(tenantDetails.getPhone())
                && tenantRepository.existsByPhone(
                        tenantDetails.getPhone())) {
            throw new RuntimeException(
                    "So dien thoai "
                    + tenantDetails.getPhone()
                    + " da duoc su dung!"
            );
        }

        if (!tenant.getIdentityNumber().equals(
                tenantDetails.getIdentityNumber())
                && tenantRepository.existsByIdentityNumber(
                        tenantDetails.getIdentityNumber())) {
            throw new RuntimeException(
                    "So CCCD "
                    + tenantDetails.getIdentityNumber()
                    + " da ton tai!"
            );
        }

        tenant.setFullName(tenantDetails.getFullName());
        tenant.setPhone(tenantDetails.getPhone());
        tenant.setIdentityNumber(
                tenantDetails.getIdentityNumber());
        tenant.setEmail(tenantDetails.getEmail());
        tenant.setAddress(tenantDetails.getAddress());
        tenant.setDateOfBirth(tenantDetails.getDateOfBirth());

        return tenantRepository.save(tenant);
    }

    // Xoa nguoi thue
    public void deleteTenant(Long id) {
        Tenant tenant = getTenantById(id);
        tenantRepository.delete(tenant);
    }
}
