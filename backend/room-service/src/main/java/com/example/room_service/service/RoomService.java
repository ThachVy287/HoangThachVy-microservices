
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

    // Chuyen Entity Room thanh DTO RoomResponse
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

    // Chuyen DTO RoomRequest thanh Entity Room
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

    // Lay danh sach phong
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Lay phong theo ID
    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Khong tim thay phong tro co ID: " + id
                        )
                );

        return convertToResponse(room);
    }

    // Them phong moi
    public RoomResponse createRoom(RoomRequest request) {

        if (roomRepository.existsByRoomCode(request.getRoomCode())) {
            throw new DuplicateResourceException(
                    "Ma phong " + request.getRoomCode() + " da ton tai!"
            );
        }

        Room room = convertToEntity(request);
        Room savedRoom = roomRepository.save(room);

        return convertToResponse(savedRoom);
    }

    // Cap nhat thong tin phong
    public RoomResponse updateRoom(Long id, RoomRequest request) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Khong tim thay phong tro co ID: " + id
                        )
                );

        if (!room.getRoomCode().equals(request.getRoomCode())
                && roomRepository.existsByRoomCode(request.getRoomCode())) {

            throw new DuplicateResourceException(
                    "Ma phong " + request.getRoomCode() + " da ton tai!"
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

    // Cap nhat trang thai phong
    public RoomResponse updateRoomStatus(Long id, String status) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Khong tim thay phong tro co ID: " + id
                        )
                );

        room.setStatus(status);

        Room updatedRoom = roomRepository.save(room);

        return convertToResponse(updatedRoom);
    }

    // Xoa phong
    public void deleteRoom(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Khong tim thay phong tro co ID: " + id
                        )
                );

        roomRepository.delete(room);
    }
}
