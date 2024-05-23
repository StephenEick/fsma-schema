package com.example.fsma.controller

import com.example.fsma.model.Address
import com.example.fsma.model.FranchisorDto
import com.example.fsma.model.toFranchisor
import com.example.fsma.model.toFranchisorDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val FRANCHISOR_BASE_URL = "/api/v1/franchisor"
private const val FRANCHISOR_ALT_BASE_URL = "/api/v1/franchi"

@RestController
@RequestMapping(value = [FRANCHISOR_BASE_URL, FRANCHISOR_ALT_BASE_URL])
//@SecurityRequirement(name = "bearerAuth")
class FranchisorController : BaseController() {

    // -- Return a specific Franchisor
    // -    http://localhost:8080/api/v1/franchisor/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<FranchisorDto> {
        val franchisor = franchisorService.findById(id)
            ?: throw EntityNotFoundException("Franchisor not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(franchisor.toFranchisorDto())
    }

    // -- Create a new Franchisor
    @PostMapping
    fun create(
        @Valid @RequestBody franchisorDto: FranchisorDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<FranchisorDto> {
        val address = addressService.findById(franchisorDto.addressId)
            ?: throw EntityNotFoundException("Franchisor Address not found: ${franchisorDto.addressId}")

        var billingAddress: Address? = null
        if (franchisorDto.billingAddressId != null)
            billingAddress = businessService.findById(franchisorDto.billingAddressId)?.let {
                throw EntityNotFoundException("ServiceAddress not found: $it")
            }
        val franchisor = franchisorDto.toFranchisor(address, billingAddress)
        val franchisorResponse = franchisorService.insert(franchisor).toFranchisorDto()
        return ResponseEntity.created(URI.create(FRANCHISOR_BASE_URL.plus("/${franchisorResponse.id}")))
            .body(franchisorResponse)
    }

    // -- Update an existing Franchisor
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody franchisorDto: FranchisorDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<FranchisorDto> {
        if (franchisorDto.id <= 0L || franchisorDto.id != id)
            throw UnauthorizedRequestException("Conflicting FranchisorIds specified: $id != ${franchisorDto.id}")

        val address = addressService.findById(franchisorDto.addressId)
            ?: throw EntityNotFoundException("Franchisor Address not found: ${franchisorDto.addressId}")
        var billingAddress: Address? = null
        if (franchisorDto.billingAddressId != null)
            billingAddress = businessService.findById(franchisorDto.billingAddressId)?.let {
                throw EntityNotFoundException("ServiceAddress not found: $it")
            }
        val franchisor = franchisorDto.toFranchisor(address, billingAddress)
        val franchisorResponse = franchisorService.update(franchisor).toFranchisorDto()
        return ResponseEntity.ok().body(franchisorResponse)
    }

    // -- Delete an existing Franchisor
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        franchisorService.findById(id)?.let { franchisor ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            franchisorService.delete(franchisor) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}