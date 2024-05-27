package com.safefood204.service

import com.safefood204.model.Address
import com.safefood204.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(
     addressRepository: AddressRepository
) : BaseService<Address>(addressRepository, "Address")
