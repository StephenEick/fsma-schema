package com.safefood204.controller

import com.safefood204.model.FsmaUser
import com.safefood204.model.TraceLotCodeDto
import com.safefood204.model.toTraceLotCode
import com.safefood204.model.toTraceLotCodeDto
import com.safefood204.util.EntityNotFoundException
import com.safefood204.util.UnauthorizedRequestException
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val TRACE_LOT_CODE_BASE_URL = "/api/v1/tracelotcode"
private const val TRACE_LOT_CODE_ALT_BASE_URL = "/api/v1/trace-lot-code"
private const val TRACE_LOT_CODE_ALT2_BASE_URL = "/api/v1/tlc"

@RestController
@RequestMapping(value = [com.safefood204.controller.TRACE_LOT_CODE_BASE_URL, com.safefood204.controller.TRACE_LOT_CODE_ALT_BASE_URL, com.safefood204.controller.TRACE_LOT_CODE_ALT2_BASE_URL])
@SecurityRequirement(name = "bearerAuth")
class TraceLotCodeController : com.safefood204.controller.BaseController() {

    // -- Return a specific TraceLotCode
    // -    http://localhost:8080/api/v1/tlc/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<TraceLotCodeDto> {
        val traceLotCode = traceLotCodeService.findById(id)
            ?: throw EntityNotFoundException("TraceLotCode not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(traceLotCode.toTraceLotCodeDto())
    }

    // -- Create a new TraceLotCode
    @PostMapping
    fun create(
        @Valid @RequestBody traceLotCodeDto: TraceLotCodeDto,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<TraceLotCodeDto> {
        val traceLotCode = traceLotCodeDto.toTraceLotCode()
        val traceLotCodeResponse = traceLotCodeService.insert(traceLotCode).toTraceLotCodeDto()
        return ResponseEntity.created(URI.create(com.safefood204.controller.TRACE_LOT_CODE_BASE_URL.plus("/${traceLotCodeResponse.id}")))
            .body(traceLotCodeResponse)
    }

    // -- Update an existing TraceLotCode
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody traceLotCodeDto: TraceLotCodeDto,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<TraceLotCodeDto> {
        if (traceLotCodeDto.id <= 0L || traceLotCodeDto.id != id)
            throw UnauthorizedRequestException("Conflicting TraceLotCodeIds specified: $id != ${traceLotCodeDto.id}")
        val traceLotCode = traceLotCodeDto.toTraceLotCode()
        val traceLotCodeResponse = traceLotCodeService.update(traceLotCode).toTraceLotCodeDto()
        return ResponseEntity.ok().body(traceLotCodeResponse)
    }

    // -- Delete an existing TraceLotCode
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<Void> {
        traceLotCodeService.findById(id)?.let { traceLotCode ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            traceLotCodeService.delete(traceLotCode) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}