package com.example.fsma.controller

import com.example.fsma.model.AddressRequestDto
import com.example.fsma.model.AddressResponseDto
import com.example.fsma.model.toAddress
import com.example.fsma.model.toAddressResponseDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
private const val LOCATION_BASE_URL = "/api/v1/location"

@RestController
@RequestMapping(LOCATION_BASE_URL)
//@SecurityRequirement(name = "bearerAuth")
class LocationController : BaseController() {

    // -- Return a specific Address
    // -    http://localhost:8080/api/v1/addresses/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<AddressResponseDto> {
        val address = addressService.findById(id)
            ?: throw EntityNotFoundException("Address not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(address.toAddressResponseDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody addressRequestDto: AddressRequestDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<AddressResponseDto> {
        val address = addressRequestDto.toAddress()
        val addressResponse = addressService.insert(address).toAddressResponseDto()
        return ResponseEntity.created(URI.create(LOCATION_BASE_URL.plus("/${addressResponse.id}")))
            .body(addressResponse)
    }

    // -- Update an existing Address
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody addressRequestDto: AddressRequestDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<AddressResponseDto> {
        if (addressRequestDto.id <= 0L || addressRequestDto.id != id)
            throw UnauthorizedRequestException("Conflicting AddressIds specified: $id != ${addressRequestDto.id}")
        val address = addressRequestDto.toAddress()
        val addressResponse = addressService.update(address).toAddressResponseDto()
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