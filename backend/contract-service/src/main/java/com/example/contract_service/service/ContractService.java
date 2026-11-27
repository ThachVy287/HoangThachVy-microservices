
package com.example.contract_service.service;

import com.example.contract_service.client.RoomClient;
import com.example.contract_service.client.TenantClient;
import com.example.contract_service.dto.RoomResponse;
import com.example.contract_service.dto.TenantResponse;
import com.example.contract_service.dto.request.ContractRequest;
import com.example.contract_service.dto.response.ContractResponse;
import com.example.contract_service.entity.Contract;
import com.example.contract_service.repository.ContractRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final RoomClient roomClient;
    private final TenantClient tenantClient;

    public ContractService(
            ContractRepository contractRepository,
            RoomClient roomClient,
            TenantClient tenantClient) {

        this.contractRepository = contractRepository;
        this.roomClient = roomClient;
        this.tenantClient = tenantClient;
    }

    // Tao hop dong moi
    @Transactional
    public ContractResponse createContract(ContractRequest request) {

        // Kiem tra ngay ket thuc phai sau ngay bat dau
        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new RuntimeException(
                    "Ngay ket thuc phai sau ngay bat dau");
        }

        // Goi Room Service de lay thong tin phong
        RoomResponse room = roomClient.getRoomById(request.getRoomId());

        if (room == null) {
            throw new RuntimeException("Khong tim thay phong");
        }

        // Kiem tra trang thai phong
        if (!"AVAILABLE".equalsIgnoreCase(room.getStatus())) {
            throw new RuntimeException(
                    "Phong hien tai khong trong, khong the tao hop dong");
        }

        // Kiem tra phong da co hop dong ACTIVE chua
        if (contractRepository.existsByRoomIdAndStatus(
                request.getRoomId(), "ACTIVE")) {

            throw new RuntimeException(
                    "Phong da co hop dong ACTIVE");
        }

        // Goi Tenant Service de lay thong tin nguoi thue
        TenantResponse tenant =
                tenantClient.getTenantById(request.getTenantId());

        if (tenant == null) {
            throw new RuntimeException("Khong tim thay nguoi thue");
        }

        // Tao doi tuong hop dong
        Contract contract = new Contract();

        contract.setContractCode(generateContractCode());
        contract.setRoomId(room.getId());
        contract.setTenantId(tenant.getId());

        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());

        // Lay gia thue truc tiep tu Room Service
        contract.setMonthlyRent(room.getMonthlyPrice());

        contract.setDeposit(request.getDeposit());
        contract.setStatus("ACTIVE");

        // Luu hop dong vao database
        Contract savedContract = contractRepository.save(contract);

        // Cap nhat trang thai phong thanh RENTED
        roomClient.updateRoomStatus(room.getId(), "RENTED");

        // Tra ve thong tin hop dong
        return convertToResponse(savedContract, room, tenant);
    }

    // Lay danh sach hop dong
    public List<ContractResponse> getAllContracts() {

        return contractRepository.findAll()
                .stream()
                .map(contract -> {

                    RoomResponse room =
                            roomClient.getRoomById(contract.getRoomId());

                    TenantResponse tenant =
                            tenantClient.getTenantById(contract.getTenantId());

                    return convertToResponse(contract, room, tenant);
                })
                .toList();
    }

    // Lay hop dong theo ID
    public ContractResponse getContractById(Long id) {

        Contract contract = contractRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Khong tim thay hop dong co ID: " + id));

        RoomResponse room =
                roomClient.getRoomById(contract.getRoomId());

        TenantResponse tenant =
                tenantClient.getTenantById(contract.getTenantId());

        return convertToResponse(contract, room, tenant);
    }

    // Xoa hop dong theo ID
    @Transactional
    public void deleteContract(Long id) {

        Contract contract = contractRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Khong tim thay hop dong co ID: " + id));

        Long roomId = contract.getRoomId();

        // Xoa hop dong trong database
        contractRepository.delete(contract);

        // Kiem tra con hop dong ACTIVE nao cua phong khong
        boolean hasActiveContract =
                contractRepository.existsByRoomIdAndStatus(
                        roomId, "ACTIVE");

        // Neu khong con hop dong ACTIVE thi mo lai phong
        if (!hasActiveContract) {
            roomClient.updateRoomStatus(roomId, "AVAILABLE");
        }
    }

    // Tao ma hop dong tu dong
    private String generateContractCode() {

        String date = LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd"));

        String random = UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();

        return "HD" + date + random;
    }

    // Chuyen Entity thanh DTO Response
    private ContractResponse convertToResponse(
            Contract contract,
            RoomResponse room,
            TenantResponse tenant) {

        ContractResponse response = new ContractResponse();

        response.setId(contract.getId());
        response.setContractCode(contract.getContractCode());

        response.setRoomId(contract.getRoomId());
        response.setRoomCode(room.getRoomCode());
        response.setRoomName(room.getRoomName());

        response.setTenantId(contract.getTenantId());
        response.setTenantName(tenant.getFullName());
        response.setTenantPhone(tenant.getPhone());

        response.setStartDate(contract.getStartDate());
        response.setEndDate(contract.getEndDate());

        response.setMonthlyRent(contract.getMonthlyRent());
        response.setDeposit(contract.getDeposit());

        response.setStatus(contract.getStatus());
        response.setCreatedAt(contract.getCreatedAt());

        return response;
    }
}
