package com.example.fsma.controller

import com.example.fsma.model.FoodBusinessDto
import com.example.fsma.model.toBusiness
import com.example.fsma.model.toFoodBusinessDto
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
    ): ResponseEntity<FoodBusinessDto> {
        val business = businessService.findById(id)
            ?: throw EntityNotFoundException("business not found = $id")
//        assertResellerClientMatchesToken(fsaUser, business.resellerId)
        return ResponseEntity.ok(business.toFoodBusinessDto())
    }

    // -- Create a new business
    @PostMapping
    fun create(
        @Valid @RequestBody foodBusinessDto: FoodBusinessDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<FoodBusinessDto> {
        val mainAddress = addressService.findById(foodBusinessDto.mainAddressId)
            ?: throw EntityNotFoundException("Address not found: ${foodBusinessDto.mainAddressId}")
        val business = foodBusinessDto.toBusiness(mainAddress = mainAddress)
        val businessResponse = businessService.insert(business).toFoodBusinessDto()
        return ResponseEntity.created(URI.create(BUSINESS_BASE_URL.plus("/${businessResponse.id}")))
            .body(businessResponse)
    }

    // -- Update an existing business
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody foodBusinessDto: FoodBusinessDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<FoodBusinessDto> {
        if (foodBusinessDto.id <= 0L || foodBusinessDto.id != id)
            throw UnauthorizedRequestException("Conflicting BusinessIds specified: $id != ${foodBusinessDto.id}")
        val mainAddress = addressService.findById(foodBusinessDto.mainAddressId)
            ?: throw EntityNotFoundException("Address not found: ${foodBusinessDto.mainAddressId}")
        val business = foodBusinessDto.toBusiness(mainAddress = mainAddress)
        val businessResponseDto = businessService.update(business).toFoodBusinessDto()
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