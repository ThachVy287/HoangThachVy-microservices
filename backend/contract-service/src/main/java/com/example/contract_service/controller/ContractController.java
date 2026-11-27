
package com.example.contract_service.controller;

import com.example.contract_service.dto.request.ContractRequest;
import com.example.contract_service.dto.response.ContractResponse;
import com.example.contract_service.service.ContractService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    // Lay danh sach hop dong
    @GetMapping
    public ResponseEntity<List<ContractResponse>> getAllContracts() {

        List<ContractResponse> contracts =
                contractService.getAllContracts();

        return ResponseEntity.ok(contracts);
    }

    // Lay hop dong theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ContractResponse> getContractById(
            @PathVariable Long id) {

        ContractResponse contract =
                contractService.getContractById(id);

        return ResponseEntity.ok(contract);
    }

    // Tao hop dong moi
    @PostMapping
    public ResponseEntity<ContractResponse> createContract(
            @Valid @RequestBody ContractRequest request) {

        ContractResponse response =
                contractService.createContract(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Xoa hop dong theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(
            @PathVariable Long id) {

        contractService.deleteContract(id);

        return ResponseEntity.noContent().build();
    }
}
