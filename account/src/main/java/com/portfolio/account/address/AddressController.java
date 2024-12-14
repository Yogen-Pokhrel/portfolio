package com.portfolio.account.address;


import com.portfolio.account.address.dto.request.CreateAddressDto;
import com.portfolio.account.address.dto.request.UpdateAddressDto;
import com.portfolio.account.address.dto.response.AddressDetailDto;
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
    public ApiResponse<List<AddressDetailDto>> getAll() {
        List<AddressDetailDto> addressDetailDtos = addressService.findAll();
        return ApiResponse.success(
                addressDetailDtos,
                "Users fetched successfully"
        );
    }

    @PostMapping
    public ApiResponse<AddressDetailDto> createAddress(
            @RequestBody CreateAddressDto address
        ) throws Exception{
        AddressDetailDto newAddressDto = addressService.save(address);
        return ApiResponse.success(newAddressDto, "Address created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<AddressDetailDto> updateAddress(@PathVariable int id, @RequestBody UpdateAddressDto updateAddressDto) throws Exception{
        AddressDetailDto addressDetailDto = addressService.update(id, updateAddressDto);
        return ApiResponse.success(addressDetailDto, "Address updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Integer id) throws Exception{
        addressService.delete(id);
        return ApiResponse.success("Successfully deleted ", "true");
    }
}
