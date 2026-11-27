
package com.example.contract_service.repository;

import com.example.contract_service.entity.Contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    // Tim hop dong theo ma hop dong
    Optional<Contract> findByContractCode(String contractCode);

    // Kiem tra ma hop dong da ton tai hay chua
    boolean existsByContractCode(String contractCode);

    // Kiem tra phong da co hop dong hay chua
    boolean existsByRoomIdAndStatus(Long roomId, String status);
}
