package com.example.room_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RoomResponse {

    private Long id;
    private String roomCode;
    private String roomName;
    private Double area;
    private BigDecimal monthlyPrice;
    private String status;
    private String description;
    private LocalDateTime createdAt;

    public RoomResponse() {
    }

    public RoomResponse(
            Long id,
            String roomCode,
            String roomName,
            Double area,
            BigDecimal monthlyPrice,
            String status,
            String description,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.area = area;
        this.monthlyPrice = monthlyPrice;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
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