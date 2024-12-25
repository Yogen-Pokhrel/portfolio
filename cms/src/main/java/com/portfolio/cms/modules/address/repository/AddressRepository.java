package com.portfolio.cms.modules.address.repository;

import com.portfolio.cms.modules.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
