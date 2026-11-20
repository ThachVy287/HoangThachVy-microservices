
package com.example.room_service.controller;

import com.example.room_service.entity.Room;
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

    // API 1: Lấy danh sách phòng
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // API 2: Lấy thông tin phòng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    // API 3: Thêm phòng mới
    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        Room newRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    // API 4: Cập nhật thông tin phòng
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody Room room) {

        return ResponseEntity.ok(roomService.updateRoom(id, room));
    }

    // API 5: Xóa phòng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
