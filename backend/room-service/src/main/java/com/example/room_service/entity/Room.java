
package com.example.room_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã phòng không được để trống")
    @Column(name = "room_code", nullable = false, unique = true, length = 20)
    private String roomCode;

    @NotBlank(message = "Tên phòng không được để trống")
    @Column(name = "room_name", nullable = false, length = 100)
    private String roomName;

    @NotNull(message = "Diện tích không được để trống")
    @Positive(message = "Diện tích phải lớn hơn 0")
    @Column(name = "area", nullable = false)
    private Double area;

    @NotNull(message = "Giá thuê không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá thuê phải lớn hơn 0")
    @Column(name = "monthly_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal monthlyPrice;

    @NotBlank(message = "Trạng thái phòng không được để trống")
    @Column(name = "status", nullable = false, length = 30)
    private String status = "AVAILABLE";

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Room() {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
