package com.example.fsma.controller

import com.example.fsma.model.LocationDto
import com.example.fsma.model.toLocation
import com.example.fsma.model.toLocationDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
private const val LOCATION_BASE_URL = "/api/v1/location"
private const val LOCATION_ALT_BASE_URL = "/api/v1/loc"


@RestController
@RequestMapping(value=[LOCATION_BASE_URL,LOCATION_ALT_BASE_URL])
//@SecurityRequirement(name = "bearerAuth")
class LocationController : BaseController() {

    // -- Return a specific Location
    // -    http://localhost:8080/api/v1/location/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<LocationDto> {
        val location = locationService.findById(id)
            ?: throw EntityNotFoundException("Location not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(location.toLocationDto())
    }

    // -- Create a new Location
    @PostMapping
    fun create(
        @Valid @RequestBody locationDto: LocationDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<LocationDto> {
        val serviceAddress = addressService.findById(locationDto.serviceAddressId)
            ?: throw EntityNotFoundException("Service Address not found: ${locationDto.serviceAddressId}")
        val business = businessService.findById(locationDto.businessId)
            ?: throw EntityNotFoundException("ServiceAddress not found: ${locationDto.serviceAddressId}")
        val location = locationDto.toLocation( business, serviceAddress)
        val locationResponse = locationService.insert(location).toLocationDto()
        return ResponseEntity.created(URI.create(LOCATION_BASE_URL.plus("/${locationResponse.id}")))
            .body(locationResponse)
    }

    // -- Update an existing Location
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody locationDto: LocationDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<LocationDto> {
        if (locationDto.id <= 0L || locationDto.id != id)
            throw UnauthorizedRequestException("Conflicting LocationIds specified: $id != ${locationDto.id}")

        val business = businessService.findById(locationDto.businessId)
            ?: throw EntityNotFoundException("Business not found: ${locationDto.businessId}")

        val serviceAddress = addressService.findById(locationDto.serviceAddressId)
            ?: throw EntityNotFoundException("ServiceAddress not found: ${locationDto.serviceAddressId}")

        val location = locationDto.toLocation(business, serviceAddress)
        val locationResponse = locationService.update(location).toLocationDto()
        return ResponseEntity.ok().body(locationResponse)
    }

    // -- Delete an existing Location
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