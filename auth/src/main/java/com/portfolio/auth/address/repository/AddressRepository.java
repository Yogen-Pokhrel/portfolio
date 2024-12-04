package com.portfolio.auth.address.repository;

import com.portfolio.auth.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
