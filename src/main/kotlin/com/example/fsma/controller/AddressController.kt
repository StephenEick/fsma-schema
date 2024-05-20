package com.example.fsma.controller

import com.example.fsma.model.AddressDto
import com.example.fsma.model.toAddress
import com.example.fsma.model.toAddressDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ADDRESS_BASE_URL = "/api/v1/address"

@RestController
@RequestMapping(ADDRESS_BASE_URL)
//@SecurityRequirement(name = "bearerAuth")
class AddressController : BaseController() {

    // -- Return a specific Address
    // -    http://localhost:8080/api/v1/addresses/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<AddressDto> {
        val address = addressService.findById(id)
            ?: throw EntityNotFoundException("Address not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(address.toAddressDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody addressDto: AddressDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<AddressDto> {
        val address = addressDto.toAddress()
        val addressResponse = addressService.insert(address).toAddressDto()
        return ResponseEntity
            .created(URI.create(ADDRESS_BASE_URL.plus("/${addressResponse.id}")))
            .body(addressResponse)
    }

    // -- Update an existing Address
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody addressDto: AddressDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<AddressDto> {
        if (addressDto.id <= 0L || addressDto.id != id)
            throw UnauthorizedRequestException("Conflicting AddressIds specified: $id != ${addressDto.id}")
        val address = addressDto.toAddress()
        val addressResponse = addressService.update(address).toAddressDto()
        return ResponseEntity.ok().body(addressResponse)
    }

    // -- Delete an existing Address
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        addressService.findById(id)?.let { address ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            addressService.delete(address) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}