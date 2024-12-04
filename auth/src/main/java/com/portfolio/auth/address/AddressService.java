package com.portfolio.auth.address;

import com.portfolio.auth.address.dto.request.CreateAddressDto;
import com.portfolio.auth.address.dto.request.UpdateAddressDto;
import com.portfolio.auth.address.dto.response.AddressDetailDto;
import com.portfolio.auth.address.entity.Address;
import com.portfolio.auth.address.repository.AddressRepository;
import com.portfolio.auth.common.CommonService;
import com.portfolio.auth.common.dto.PaginationDto;
import com.portfolio.auth.common.exception.ResourceNotFoundException;
import com.portfolio.auth.user.entity.User;
import com.portfolio.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService implements CommonService<CreateAddressDto, UpdateAddressDto, AddressDetailDto, Integer> {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AddressDetailDto> findAll() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(address -> modelMapper.map(address, AddressDetailDto.class)).toList();
    }

    @Override
    public <R> Page<R> findAll(PaginationDto paginationDto) {
        return CommonService.super.findAll(paginationDto);
    }

    @Override
    public AddressDetailDto findById(Integer id) throws Exception {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Address Exists with provided ID"));
        return modelMapper.map(address, AddressDetailDto.class);
    }

    @Override
    public AddressDetailDto save(CreateAddressDto address) throws Exception {
        User user = userRepository.findById(address.getUserId()).orElseThrow(() -> new ResourceNotFoundException("No User Exists with provided ID"));
        Address newAddress = modelMapper.map(address, Address.class);
        newAddress.setUser(user);
         addressRepository.save(newAddress);
        return modelMapper.map(newAddress, AddressDetailDto.class);
    }

    @Override
    public AddressDetailDto update(Integer id, UpdateAddressDto updateAddressDto) throws Exception {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        Address address;
        if(optionalAddress.isPresent()) {
            address = optionalAddress.get();

            if(updateAddressDto.getStreet() != null) {
                address.setStreet(updateAddressDto.getStreet());
            }
            if(updateAddressDto.getCity() != null) {
                address.setCity(updateAddressDto.getCity());
            }
            if(updateAddressDto.getState() != null) {
                address.setState(updateAddressDto.getState());
            }
            if(updateAddressDto.getCountry() != null) {
                address.setCountry(updateAddressDto.getCountry());
            }
            if(updateAddressDto.getZipcode() != null) {
                address.setZipcode(updateAddressDto.getZipcode());
            }
            address = addressRepository.save(address);
        }else{
            throw new ResourceNotFoundException("No Address Exists with provided ID");
        }
        return modelMapper.map(address, AddressDetailDto.class);
    }

    @Override
    public void delete(Integer id) throws Exception {
        addressRepository.deleteById(id);
    }
}
