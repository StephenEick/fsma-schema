package com.example.fsma.controller

import com.example.fsma.model.BusinessDto
import com.example.fsma.model.toBusiness
import com.example.fsma.model.toBusinessDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val BUSINESS_BASE_URL = "/api/v1/business"

@RestController
@RequestMapping(BUSINESS_BASE_URL)
//@SecurityRequirement(name = "bearerAuth")
class BusinessController : BaseController() {

    // -- Return a specific business
    // -    http://localhost:8080/api/v1/business/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<BusinessDto> {
        val business = businessService.findById(id)
            ?: throw EntityNotFoundException("business not found = $id")
//        assertResellerClientMatchesToken(fsaUser, business.resellerId)
        return ResponseEntity.ok(business.toBusinessDto())
    }

    // -- Create a new business
    @PostMapping
    fun create(
        @Valid @RequestBody businessDto: BusinessDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<BusinessDto> {
        val mainAddress = addressService.findById(businessDto.mainAddressId)
            ?: throw EntityNotFoundException("Address not found: ${businessDto.mainAddressId}")
        val business = businessDto.toBusiness(mainAddress = mainAddress)
        val businessResponse = businessService.insert(business).toBusinessDto()
        return ResponseEntity.created(URI.create(BUSINESS_BASE_URL.plus("/${businessResponse.id}")))
            .body(businessResponse)
    }

    // -- Update an existing business
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody businessDto: BusinessDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<BusinessDto> {
        if (businessDto.id <= 0L || businessDto.id != id)
            throw UnauthorizedRequestException("Conflicting BusinessIds specified: $id != ${businessDto.id}")
        val mainAddress = addressService.findById(businessDto.mainAddressId)
            ?: throw EntityNotFoundException("Address not found: ${businessDto.mainAddressId}")
        val business = businessDto.toBusiness(mainAddress = mainAddress)
        val businessResponseDto = businessService.update(business).toBusinessDto()
        return ResponseEntity.ok().body(businessResponseDto)
    }

    // -- Delete an existing business
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        businessService.findById(id)?.let { business ->
//            assertResellerClientMatchesToken(fsaUser, business.resellerId)
            businessService.delete(business) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}