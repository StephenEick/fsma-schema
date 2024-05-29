package com.foodtraceai.service

import com.foodtraceai.model.Address
import com.foodtraceai.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(
     addressRepository: AddressRepository
) : BaseService<Address>(addressRepository, "Address")
