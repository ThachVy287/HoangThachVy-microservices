
package com.example.room_service.controller;

import com.example.room_service.dto.RoomRequest;
import com.example.room_service.dto.RoomResponse;
import com.example.room_service.dto.RoomStatusRequest;
import com.example.room_service.service.RoomService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // API 1: Lay danh sach phong
    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // API 2: Lay thong tin phong theo ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(
            @PathVariable Long id) {

        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    // API 3: Them phong moi
    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @Valid @RequestBody RoomRequest request) {

        RoomResponse newRoom = roomService.createRoom(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newRoom);
    }

    // API 4: Cap nhat thong tin phong
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequest request) {

        return ResponseEntity.ok(
                roomService.updateRoom(id, request)
        );
    }

    // API 5: Cap nhat trang thai phong
    @PatchMapping("/{id}/status")
    public ResponseEntity<RoomResponse> updateRoomStatus(
            @PathVariable Long id,
            @Valid @RequestBody RoomStatusRequest request) {

        return ResponseEntity.ok(
                roomService.updateRoomStatus(
                        id,
                        request.getStatus()
                )
        );
    }

    // API 6: Xoa phong
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.noContent().build();
    }
}
