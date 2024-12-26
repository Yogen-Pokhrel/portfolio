package com.portfolio.cms.modules.address;


import com.portfolio.cms.modules.address.dto.request.AddressCreateUpdateDto;
import com.portfolio.cms.modules.address.dto.response.AddressResponseDto;
import com.portfolio.core.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ApiResponse<List<AddressResponseDto>> getAll() {
        List<AddressResponseDto> addressDetailDto = addressService.findAll();
        return ApiResponse.success(
                addressDetailDto,
                "Users fetched successfully"
        );
    }

    @PostMapping
    public ApiResponse<AddressResponseDto> createAddress(
            @RequestBody AddressCreateUpdateDto address
        ) throws Exception{
        AddressResponseDto newAddressDto = addressService.save(address);
        return ApiResponse.success(newAddressDto, "Address created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<AddressResponseDto> updateAddress(@PathVariable int id, @RequestBody AddressCreateUpdateDto updateAddressDto) throws Exception{
        AddressResponseDto addressDetailDto = addressService.update(id, updateAddressDto, false);
        return ApiResponse.success(addressDetailDto, "Address updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Integer id) throws Exception{
        addressService.delete(id);
        return ApiResponse.success("Successfully deleted ", "true");
    }
}
