
package com.example.room_service.service;

import com.example.room_service.dto.RoomRequest;
import com.example.room_service.dto.RoomResponse;
import com.example.room_service.entity.Room;
import com.example.room_service.exception.DuplicateResourceException;
import com.example.room_service.exception.ResourceNotFoundException;
import com.example.room_service.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Chuyển Entity Room thành DTO RoomResponse
    private RoomResponse convertToResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getRoomCode(),
                room.getRoomName(),
                room.getArea(),
                room.getMonthlyPrice(),
                room.getStatus(),
                room.getDescription(),
                room.getCreatedAt()
        );
    }

    // Chuyển DTO RoomRequest thành Entity Room
    private Room convertToEntity(RoomRequest request) {
        Room room = new Room();

        room.setRoomCode(request.getRoomCode());
        room.setRoomName(request.getRoomName());
        room.setArea(request.getArea());
        room.setMonthlyPrice(request.getMonthlyPrice());
        room.setStatus(request.getStatus());
        room.setDescription(request.getDescription());

        return room;
    }

    // Lấy danh sách phòng
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Lấy phòng theo ID
    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy phòng trọ có ID: " + id
                        )
                );

        return convertToResponse(room);
    }

    // Thêm phòng mới
    public RoomResponse createRoom(RoomRequest request) {

        if (roomRepository.existsByRoomCode(request.getRoomCode())) {
            throw new DuplicateResourceException(
                    "Mã phòng " + request.getRoomCode() + " đã tồn tại!"
            );
        }

        Room room = convertToEntity(request);
        Room savedRoom = roomRepository.save(room);

        return convertToResponse(savedRoom);
    }

    // Cập nhật phòng
    public RoomResponse updateRoom(Long id, RoomRequest request) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy phòng trọ có ID: " + id
                        )
                );

        if (!room.getRoomCode().equals(request.getRoomCode())
                && roomRepository.existsByRoomCode(request.getRoomCode())) {

            throw new DuplicateResourceException(
                    "Mã phòng " + request.getRoomCode() + " đã tồn tại!"
            );
        }

        room.setRoomCode(request.getRoomCode());
        room.setRoomName(request.getRoomName());
        room.setArea(request.getArea());
        room.setMonthlyPrice(request.getMonthlyPrice());
        room.setStatus(request.getStatus());
        room.setDescription(request.getDescription());

        Room updatedRoom = roomRepository.save(room);

        return convertToResponse(updatedRoom);
    }

    // Xóa phòng
    public void deleteRoom(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy phòng trọ có ID: " + id
                        )
                );

        roomRepository.delete(room);
    }
}
