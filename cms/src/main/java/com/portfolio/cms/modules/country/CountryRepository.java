package com.portfolio.cms.modules.country;

import com.portfolio.cms.modules.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByIsoCode(String isoCode);
}
