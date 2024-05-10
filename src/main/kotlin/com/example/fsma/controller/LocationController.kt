package com.example.fsma.controller

import com.example.fsma.model.LocationRequestDto
import com.example.fsma.model.LocationResponseDto
import com.example.fsma.model.toLocation
import com.example.fsma.model.toLocationResponseDto
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
    ): ResponseEntity<LocationResponseDto> {
        val location = locationService.findById(id)
            ?: throw EntityNotFoundException("ServiceAddress not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(location.toLocationResponseDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody locationRequestDto: LocationRequestDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<LocationResponseDto> {
        val serviceAddress = addressService.findById(locationRequestDto.serviceAddressId)
            ?: throw EntityNotFoundException("Service Address not found: ${locationRequestDto.serviceAddressId}")
        val business = businessService.findById(locationRequestDto.businessId)
            ?: throw EntityNotFoundException("ServiceAddress not found: ${locationRequestDto.serviceAddressId}")
        val location = locationRequestDto.toLocation(business = business, serviceAddress = serviceAddress)
        val locationResponse = locationService.insert(location).toLocationResponseDto()
        return ResponseEntity.created(URI.create(LOCATION_BASE_URL.plus("/${locationResponse.id}")))
            .body(locationResponse)
    }

    // -- Update an existing Location
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody locationRequestDto: LocationRequestDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<LocationResponseDto> {
        if (locationRequestDto.id <= 0L || locationRequestDto.id != id)
            throw UnauthorizedRequestException("Conflicting LocationIds specified: $id != ${locationRequestDto.id}")

        val business = businessService.findById(locationRequestDto.businessId)
            ?: throw EntityNotFoundException("Business not found: ${locationRequestDto.businessId}")

        val serviceAddress = addressService.findById(locationRequestDto.serviceAddressId)
            ?: throw EntityNotFoundException("ServiceAddress not found: ${locationRequestDto.serviceAddressId}")

        val location = locationRequestDto.toLocation(business, serviceAddress)
        val locationResponse = locationService.update(location).toLocationResponseDto()
        return ResponseEntity.ok().body(locationResponse)
    }

    // -- Delete an existing Address
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        locationService.findById(id)?.let { location ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            locationService.delete(location) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}