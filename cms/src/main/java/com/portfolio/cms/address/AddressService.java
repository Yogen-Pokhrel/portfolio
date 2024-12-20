package com.portfolio.cms.address;

import com.portfolio.cms.address.dto.request.CreateAddressDto;
import com.portfolio.cms.address.dto.request.UpdateAddressDto;
import com.portfolio.cms.address.dto.response.AddressResponseDto;
import com.portfolio.cms.address.entity.Address;
import com.portfolio.cms.address.repository.AddressRepository;
import com.portfolio.core.service.SimpleCrudService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends SimpleCrudService<AddressRepository, Address, CreateAddressDto, UpdateAddressDto, AddressResponseDto, Integer> {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper) {
        super(addressRepository, Address.class, AddressResponseDto.class);
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }
}
