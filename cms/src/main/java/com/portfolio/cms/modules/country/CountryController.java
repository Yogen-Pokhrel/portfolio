package com.portfolio.cms.modules.country;

import com.portfolio.cms.modules.country.dto.CountryCreateUpdateDto;
import com.portfolio.cms.modules.country.dto.CountryResponseDto;
import com.portfolio.core.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ApiResponse<Page<CountryResponseDto>> getCountries(Pageable pageable) {
        return ApiResponse.success(countryService.findAll(pageable), "Countries fetched successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<CountryResponseDto> getCountryById(@PathVariable Integer id) {
        return ApiResponse.success(countryService.findById(id), "Country fetched successfully");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CountryResponseDto> createCountry(@RequestBody @Valid CountryCreateUpdateDto countryCreateUpdateDto) {
        return ApiResponse.success(countryService.save(countryCreateUpdateDto), "Country created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<CountryResponseDto> updateCountry( @PathVariable Integer id, @RequestBody @Valid CountryCreateUpdateDto countryCreateUpdateDto) {
        return ApiResponse.success(countryService.update(id, countryCreateUpdateDto, false), "Country updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<CountryResponseDto> deleteCountry( @PathVariable Integer id) {
        return ApiResponse.success(countryService.delete(id), "Country deleted successfully");
    }
}
