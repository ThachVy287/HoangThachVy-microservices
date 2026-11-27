
package com.example.room_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RoomStatusRequest {

    @NotBlank(message = "Trang thai phong khong duoc de trong")
    @Pattern(
            regexp = "AVAILABLE|RENTED",
            message = "Trang thai chi duoc la AVAILABLE hoac RENTED"
    )
    private String status;

    public RoomStatusRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
