package com.example.fsma.controller.cte

import com.example.fsma.controller.BaseController
import com.example.fsma.model.FsmaUser
import com.example.fsma.model.cte.CteCoolDto
import com.example.fsma.model.cte.toCteCool
import com.example.fsma.model.cte.toCteCoolDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val CTE_COOL_BASE_URL = "/api/v1/ctecool"
private const val CTE_COOL_ALT_BASE_URL = "/api/v1/cte-cool"

@RestController
@RequestMapping(value = [CTE_COOL_BASE_URL, CTE_COOL_ALT_BASE_URL])
@SecurityRequirement(name = "bearerAuth")
class CteCoolController : BaseController() {

    // -- Return a specific CteCool
    // -    http://localhost:8080/api/v1/addresses/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<CteCoolDto> {
        val cteCool = cteCoolService.findById(id)
            ?: throw EntityNotFoundException("ServiceAddress not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(cteCool.toCteCoolDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody cteCoolDto: CteCoolDto,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<CteCoolDto> {
        val subsequentRecipient = locationService.findById(cteCoolDto.subsequentRecipientId)
            ?: throw EntityNotFoundException("SubsequentRecipient Location not found: ${cteCoolDto.subsequentRecipientId}")

        val cteBusName = foodBusService.findById(cteCoolDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName Business not found: ${cteCoolDto.cteBusNameId}")

        val cteCool = cteCoolDto.toCteCool(subsequentRecipient, cteBusName)
        val cteCoolResponse = cteCoolService.insert(cteCool).toCteCoolDto()
        return ResponseEntity.created(URI.create(CTE_COOL_BASE_URL.plus("/${cteCoolResponse.id}")))
            .body(cteCoolResponse)
    }

    // -- Update an existing Location
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody cteCoolDto: CteCoolDto,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<CteCoolDto> {
        if (cteCoolDto.id <= 0L || cteCoolDto.id != id)
            throw UnauthorizedRequestException("Conflicting CtcCool Ids specified: $id != ${cteCoolDto.id}")

        val subsequentRecipient = locationService.findById(cteCoolDto.subsequentRecipientId)
            ?: throw EntityNotFoundException("SubsequentRecipient Location not found: ${cteCoolDto.subsequentRecipientId}")

        val cteBusName = foodBusService.findById(cteCoolDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName Business not found: ${cteCoolDto.cteBusNameId}")

        val cteCool = cteCoolDto.toCteCool(subsequentRecipient, cteBusName)
        val cteCoolCto = cteCoolService.update(cteCool).toCteCoolDto()
        return ResponseEntity.ok().body(cteCoolCto)
    }

    // -- Delete an existing Address
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<Void> {
        cteCoolService.findById(id)?.let { ctcCoolCto ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            cteCoolService.delete(ctcCoolCto) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}