
package com.example.contract_service.client;

import com.example.contract_service.dto.RoomResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RoomClient {

    private final RestClient roomRestClient;

    public RoomClient(
            @Qualifier("roomRestClient") RestClient roomRestClient) {
        this.roomRestClient = roomRestClient;
    }

    // Lay thong tin phong theo ID tu Room Service
    public RoomResponse getRoomById(Long roomId) {

        return roomRestClient.get()
                .uri("/api/rooms/{id}", roomId)
                .retrieve()
                .body(RoomResponse.class);
    }

    // Cap nhat trang thai phong trong Room Service
    public RoomResponse updateRoomStatus(Long roomId, String status) {

        return roomRestClient.patch()
                .uri("/api/rooms/{id}/status", roomId)
                .body(java.util.Map.of("status", status))
                .retrieve()
                .body(RoomResponse.class);
    }
}
