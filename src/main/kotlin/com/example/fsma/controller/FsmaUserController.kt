package com.example.fsma.controller

import com.example.fsma.model.*
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val FSMA_USER_BASE_URL = "/api/v1/fsausers"
private const val FSMA_USER_ALT_BASE_URL = "/api/v1/fsa_users"

@RestController
@RequestMapping(value = [FSMA_USER_BASE_URL, FSMA_USER_ALT_BASE_URL])
@SecurityRequirement(name = "bearerAuth")
class FsmaUserController : BaseController() {

    // -- Return a specific FsmaUser
    // -    http://localhost:8080/api/v1/fsmaUser/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<FsmaUserResponseDto> {
        val fsma = fsmaUserService.findById(id)
            ?: throw EntityNotFoundException("FsmaUser not found = $id")
        return ResponseEntity.ok(fsma.toFsmaUserResponseDto())
    }

    // -- Create a new FsmaUser
    @PostMapping
    fun create(
        @Valid @RequestBody fsmaUserRequestDto: FsmaUserRequestDto,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<FsmaUserResponseDto> {
        val foodBusiness = foodBusService.findById(fsmaUserRequestDto.foodBusinessId)
            ?: throw EntityNotFoundException("FoodBusiness not found: ${fsmaUserRequestDto.foodBusinessId}")
        val fsmaUser = fsmaUserRequestDto.toFsmaUser(foodBusiness)
        val fsmaUserResponse = fsmaUserService.insert(fsmaUser).toFsmaUserResponseDto()
        return ResponseEntity.created(URI.create(FSMA_USER_BASE_URL.plus("/${fsmaUserResponse.id}")))
            .body(fsmaUserResponse)
    }

    // -- Update an existing FsmaUser
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody fsmaUserRequestDto: FsmaUserRequestDto,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<FsmaUserResponseDto> {
        if (fsmaUserRequestDto.id <= 0L || fsmaUserRequestDto.id != id)
            throw UnauthorizedRequestException("Conflicting FsmaUserIds specified: $id != ${fsmaUserRequestDto.id}")

        val foodBusiness = foodBusService.findById(fsmaUserRequestDto.foodBusinessId)
            ?: throw EntityNotFoundException("FoodBusiness not found: ${fsmaUserRequestDto.foodBusinessId}")

        val fsma = fsmaUserRequestDto.toFsmaUser(foodBusiness)
        val fsmaUserResponse = fsmaUserService.update(fsma).toFsmaUserResponseDto()
        return ResponseEntity.ok().body(fsmaUserResponse)
    }

    // -- Delete an existing FsmaUser
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<Void> {
        fsmaUserService.findById(id)?.let { fsmaUser ->
            fsmaUserService.delete(fsmaUser) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}