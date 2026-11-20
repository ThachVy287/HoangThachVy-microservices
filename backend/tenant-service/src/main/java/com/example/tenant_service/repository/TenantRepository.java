
package com.example.tenant_service.repository;

import com.example.tenant_service.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    boolean existsByPhone(String phone);

    boolean existsByIdentityNumber(String identityNumber);

}
