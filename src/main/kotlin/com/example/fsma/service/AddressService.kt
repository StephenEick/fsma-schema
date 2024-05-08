package com.example.fsma.service

import com.example.fsma.model.Address
import com.example.fsma.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(
     addressRepository: AddressRepository
) : BaseService<Address>(addressRepository, "Address")
