
package com.example.room_service.service;

import com.example.room_service.entity.Room;
import com.example.room_service.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Lấy danh sách tất cả phòng
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Tìm phòng theo ID
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy phòng trọ có ID: " + id));
    }

    // Thêm phòng mới
    public Room createRoom(Room room) {

        if (roomRepository.existsByRoomCode(room.getRoomCode())) {
            throw new RuntimeException(
                    "Mã phòng " + room.getRoomCode() + " đã tồn tại!");
        }

        return roomRepository.save(room);
    }

    // Cập nhật thông tin phòng
    public Room updateRoom(Long id, Room roomDetails) {

        Room room = getRoomById(id);

        if (!room.getRoomCode().equals(roomDetails.getRoomCode())
                && roomRepository.existsByRoomCode(roomDetails.getRoomCode())) {
            throw new RuntimeException(
                    "Mã phòng " + roomDetails.getRoomCode() + " đã tồn tại!");
        }

        room.setRoomCode(roomDetails.getRoomCode());
        room.setRoomName(roomDetails.getRoomName());
        room.setArea(roomDetails.getArea());
        room.setMonthlyPrice(roomDetails.getMonthlyPrice());
        room.setStatus(roomDetails.getStatus());
        room.setDescription(roomDetails.getDescription());

        return roomRepository.save(room);
    }

    // Xóa phòng
    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
}
