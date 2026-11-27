
package com.example.tenant_service.service;

import com.example.tenant_service.dto.TenantRequest;
import com.example.tenant_service.dto.TenantResponse;
import com.example.tenant_service.entity.Tenant;
import com.example.tenant_service.exception.DuplicateResourceException;
import com.example.tenant_service.exception.ResourceNotFoundException;
import com.example.tenant_service.repository.TenantRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    // Chuyen Entity sang Response DTO
    private TenantResponse convertToResponse(Tenant tenant) {
        return new TenantResponse(
                tenant.getId(),
                tenant.getFullName(),
                tenant.getPhone(),
                tenant.getIdentityNumber(),
                tenant.getEmail(),
                tenant.getAddress(),
                tenant.getDateOfBirth(),
                tenant.getCreatedAt()
        );
    }

    // Chuyen Request DTO sang Entity
    private Tenant convertToEntity(TenantRequest request) {
        Tenant tenant = new Tenant();

        tenant.setFullName(request.getFullName());
        tenant.setPhone(request.getPhone());
        tenant.setIdentityNumber(request.getIdentityNumber());
        tenant.setEmail(request.getEmail());
        tenant.setAddress(request.getAddress());
        tenant.setDateOfBirth(request.getDateOfBirth());

        return tenant;
    }

    // Lay danh sach tat ca nguoi thue
    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Tim nguoi thue theo ID
    public TenantResponse getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy người thuê có ID: " + id
                        ));

        return convertToResponse(tenant);
    }

    // Them nguoi thue moi
    public TenantResponse createTenant(TenantRequest request) {

        if (tenantRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException(
                    "Số điện thoại " + request.getPhone()
                    + " đã được sử dụng!"
            );
        }

        if (tenantRepository.existsByIdentityNumber(
                request.getIdentityNumber())) {
            throw new DuplicateResourceException(
                    "Số CCCD " + request.getIdentityNumber()
                    + " đã tồn tại!"
            );
        }

        Tenant tenant = convertToEntity(request);

        Tenant savedTenant = tenantRepository.save(tenant);

        return convertToResponse(savedTenant);
    }

    // Cap nhat thong tin nguoi thue
    public TenantResponse updateTenant(Long id, TenantRequest request) {

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy người thuê có ID: " + id
                        ));

        // Kiem tra trung so dien thoai
        if (!tenant.getPhone().equals(request.getPhone())
                && tenantRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException(
                    "Số điện thoại " + request.getPhone()
                    + " đã được sử dụng!"
            );
        }

        // Kiem tra trung so CCCD
        if (!tenant.getIdentityNumber().equals(
                request.getIdentityNumber())
                && tenantRepository.existsByIdentityNumber(
                        request.getIdentityNumber())) {
            throw new DuplicateResourceException(
                    "Số CCCD " + request.getIdentityNumber()
                    + " đã tồn tại!"
            );
        }

        tenant.setFullName(request.getFullName());
        tenant.setPhone(request.getPhone());
        tenant.setIdentityNumber(request.getIdentityNumber());
        tenant.setEmail(request.getEmail());
        tenant.setAddress(request.getAddress());
        tenant.setDateOfBirth(request.getDateOfBirth());

        Tenant updatedTenant = tenantRepository.save(tenant);

        return convertToResponse(updatedTenant);
    }

    // Xoa nguoi thue
    public void deleteTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy người thuê có ID: " + id
                        ));

        tenantRepository.delete(tenant);
    }
}
