package com.portfolio.cms.modules.country;

import com.portfolio.cms.modules.country.dto.CountryCreateUpdateDto;
import com.portfolio.cms.modules.country.dto.CountryResponseDto;
import com.portfolio.cms.modules.country.entity.Country;
import com.portfolio.core.exception.DuplicateResourceException;
import com.portfolio.core.exception.ValidationException;
import com.portfolio.core.service.SimpleCrudService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService extends SimpleCrudService<CountryRepository, Country, CountryCreateUpdateDto, CountryResponseDto, Integer> {
    private final CountryRepository countryRepository;
    public CountryService(CountryRepository repository) {
        super(repository, Country.class, CountryResponseDto.class);
        this.countryRepository = repository;
    }

    @Override
    protected void validate(Country country) throws ValidationException {
        if(country.getIsoCode() != null && !country.getIsoCode().isEmpty()) {
            Optional<Country> existingCountry = countryRepository.findByIsoCode(country.getIsoCode());
            if(existingCountry.isPresent() && !existingCountry.get().getId().equals(country.getId())) {
                throw new DuplicateResourceException("Country with ISO code `" + country.getIsoCode() + "` already exists");
            }
        }
        super.validate(country);
    }
}
