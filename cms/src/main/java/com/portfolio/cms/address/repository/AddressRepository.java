package com.portfolio.cms.address.repository;

import com.portfolio.cms.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
