
package com.example.tenant_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TenantResponse {

    private Long id;
    private String fullName;
    private String phone;
    private String identityNumber;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;

    public TenantResponse() {
    }

    public TenantResponse(
            Long id,
            String fullName,
            String phone,
            String identityNumber,
            String email,
            String address,
            LocalDate dateOfBirth,
            LocalDateTime createdAt) {

        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.identityNumber = identityNumber;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
