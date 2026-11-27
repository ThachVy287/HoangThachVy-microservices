package com.example.contract_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractRequest {

    @NotNull(message = "ID phong khong duoc de trong")
    private Long roomId;

    @NotNull(message = "ID nguoi thue khong duoc de trong")
    private Long tenantId;

    @NotNull(message = "Ngay bat dau khong duoc de trong")
    @FutureOrPresent(message = "Ngay bat dau phai tu hom nay tro di")
    private LocalDate startDate;

    @NotNull(message = "Ngay ket thuc khong duoc de trong")
    private LocalDate endDate;

    @NotNull(message = "Tien coc khong duoc de trong")
    @DecimalMin(value = "0.0", message = "Tien coc khong duoc am")
    private BigDecimal deposit;

    public ContractRequest() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }
}