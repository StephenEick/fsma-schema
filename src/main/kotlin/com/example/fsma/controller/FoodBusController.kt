package com.example.fsma.controller

import com.example.fsma.model.FoodBusDto
import com.example.fsma.model.FsmaUser
import com.example.fsma.model.toBusiness
import com.example.fsma.model.toFoodBusinessDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val BUSINESS_BASE_URL = "/api/v1/foodbus"
private const val BUSINESS_ALT_BASE_URL = "/api/v1/food-bus"

@RestController
@RequestMapping(value=[BUSINESS_BASE_URL,BUSINESS_ALT_BASE_URL])
@SecurityRequirement(name = "bearerAuth")
class FoodBusController : BaseController() {

    // -- Return a specific foodBusiness
    // -    http://localhost:8080/api/v1/business/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
@AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<FoodBusDto> {
        val business = foodBusService.findById(id)
            ?: throw EntityNotFoundException("FoodBusiness not found = $id")
//        assertResellerClientMatchesToken(fsaUser, business.resellerId)
        return ResponseEntity.ok(business.toFoodBusinessDto())
    }

    // -- Create a new business
    @PostMapping
    fun create(
        @Valid @RequestBody foodBusDto: FoodBusDto,
@AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<FoodBusDto> {
        val mainAddress = addressService.findById(foodBusDto.mainAddressId)
            ?: throw EntityNotFoundException("Address not found: ${foodBusDto.mainAddressId}")

        val franchisor = if (foodBusDto.franchisorId == null) null
            else franchisorService.findById(foodBusDto.franchisorId)
                ?: throw EntityNotFoundException("Franchisor not found: ${foodBusDto.franchisorId}")

        val business = foodBusDto.toBusiness(mainAddress,franchisor)
        val businessResponse = foodBusService.insert(business).toFoodBusinessDto()
        return ResponseEntity.created(URI.create(BUSINESS_BASE_URL.plus("/${businessResponse.id}")))
            .body(businessResponse)
    }

    // -- Update an existing business
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody foodBusDto: FoodBusDto,
@AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<FoodBusDto> {
        if (foodBusDto.id <= 0L || foodBusDto.id != id)
            throw UnauthorizedRequestException("Conflicting BusinessIds specified: $id != ${foodBusDto.id}")
        val mainAddress = addressService.findById(foodBusDto.mainAddressId)
            ?: throw EntityNotFoundException("Address not found: ${foodBusDto.mainAddressId}")
        val franchisor = if (foodBusDto.franchisorId == null) null
        else franchisorService.findById(foodBusDto.franchisorId)
            ?: throw EntityNotFoundException("Franchisor not found: ${foodBusDto.franchisorId}")

        val business = foodBusDto.toBusiness(mainAddress,franchisor)
        val businessResponseDto = foodBusService.update(business).toFoodBusinessDto()
        return ResponseEntity.ok().body(businessResponseDto)
    }

    // -- Delete an existing business
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
@AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<Void> {
        foodBusService.findById(id)?.let { business ->
//            assertResellerClientMatchesToken(fsaUser, business.resellerId)
            foodBusService.delete(business) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}