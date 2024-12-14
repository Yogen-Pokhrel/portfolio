package com.portfolio.account.address;

import com.portfolio.account.address.dto.request.CreateAddressDto;
import com.portfolio.account.address.dto.request.UpdateAddressDto;
import com.portfolio.account.address.dto.response.AddressDetailDto;
import com.portfolio.account.address.entity.Address;
import com.portfolio.account.address.repository.AddressRepository;
import com.portfolio.core.exception.ResourceNotFoundException;
import com.portfolio.account.user.entity.User;
import com.portfolio.account.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

//    @Override
    public List<AddressDetailDto> findAll() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(address -> modelMapper.map(address, AddressDetailDto.class)).toList();
    }

//    @Override
    public AddressDetailDto findById(Integer id) throws Exception {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Address Exists with provided ID"));
        return modelMapper.map(address, AddressDetailDto.class);
    }

//    @Override
    public AddressDetailDto save(CreateAddressDto address) throws Exception {
        User user = userRepository.findById(address.getUserId()).orElseThrow(() -> new ResourceNotFoundException("No User Exists with provided ID"));
        Address newAddress = modelMapper.map(address, Address.class);
        newAddress.setUser(user);
         addressRepository.save(newAddress);
        return modelMapper.map(newAddress, AddressDetailDto.class);
    }

//    @Override
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

//    @Override
    public void delete(Integer id) throws Exception {
        addressRepository.deleteById(id);
    }
}
