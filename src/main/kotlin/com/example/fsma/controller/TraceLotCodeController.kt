package com.example.fsma.controller

import com.example.fsma.model.TraceLotCodeDto
import com.example.fsma.model.toTraceLotCode
import com.example.fsma.model.toTraceLotCodeDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val TRACE_LOT_CODE_BASE_URL = "/api/v1/tracelotcode"
private const val TRACE_LOT_CODE_ALT_BASE_URL = "/api/v1/trace-lot-code"
private const val TRACE_LOT_CODE_ALT2_BASE_URL = "/api/v1/tlc"

@RestController
@RequestMapping(value = [TRACE_LOT_CODE_BASE_URL, TRACE_LOT_CODE_ALT_BASE_URL, TRACE_LOT_CODE_ALT2_BASE_URL])
//@SecurityRequirement(name = "bearerAuth")
class TraceLotCodeController : BaseController() {

    // -- Return a specific TraceLotCode
    // -    http://localhost:8080/api/v1/tlc/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
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
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<TraceLotCodeDto> {
        val traceLotCode = traceLotCodeDto.toTraceLotCode()
        val traceLotCodeResponse = traceLotCodeService.insert(traceLotCode).toTraceLotCodeDto()
        return ResponseEntity.created(URI.create(TRACE_LOT_CODE_BASE_URL.plus("/${traceLotCodeResponse.id}")))
            .body(traceLotCodeResponse)
    }

    // -- Update an existing TraceLotCode
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody traceLotCodeDto: TraceLotCodeDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
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
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        traceLotCodeService.findById(id)?.let { traceLotCode ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            traceLotCodeService.delete(traceLotCode) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}