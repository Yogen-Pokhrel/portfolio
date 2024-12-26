package com.portfolio.cms.modules.address;

import com.portfolio.cms.modules.address.dto.request.AddressCreateUpdateDto;
import com.portfolio.cms.modules.address.dto.response.AddressResponseDto;
import com.portfolio.cms.modules.address.entity.Address;
import com.portfolio.cms.modules.address.repository.AddressRepository;
import com.portfolio.core.service.SimpleCrudService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends SimpleCrudService<AddressRepository, Address, AddressCreateUpdateDto, AddressResponseDto, Integer> {

    @Autowired
    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper) {
        super(addressRepository, Address.class, AddressResponseDto.class);
    }
}
