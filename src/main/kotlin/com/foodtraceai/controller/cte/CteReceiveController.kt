package com.foodtraceai.controller.cte

import com.foodtraceai.controller.BaseController
import com.foodtraceai.model.FsmaUser
import com.foodtraceai.model.cte.CteReceiveDto
import com.foodtraceai.model.cte.toCteReceive
import com.foodtraceai.model.cte.toCteReceiveDto
import com.foodtraceai.util.EntityNotFoundException
import com.foodtraceai.util.UnauthorizedRequestException
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val CTE_RECEIVE_BASE_URL = "/api/v1/ctereceive"
private const val CTE_RECEIVE_ALT_BASE_URL = "/api/v1/cte/receive"

@RestController
@RequestMapping(value = [CTE_RECEIVE_BASE_URL, CTE_RECEIVE_ALT_BASE_URL])
@SecurityRequirement(name = "bearerAuth")
class CteReceiveController : BaseController() {

    // -- Return a specific CteCool
    // -    http://localhost:8080/api/v1/addresses/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<CteReceiveDto> {
        val cteReceive = cteReceiveService.findById(id)
            ?: throw EntityNotFoundException("CteReceive not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(cteReceive.toCteReceiveDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody cteReceiveDto: CteReceiveDto,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<CteReceiveDto> {
        val cteBusName = foodBusService.findById(cteReceiveDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteReceiveDto.cteBusNameId}")

        val traceLotCode = traceLotCodeService.findById(cteReceiveDto.tlcId)
            ?: throw EntityNotFoundException("TraceLotCode not found: ${cteReceiveDto.tlcId}")

        val shipFromLocation = locationService.findById(cteReceiveDto.shipFromLocationId)
            ?: throw EntityNotFoundException("ShipFromLocation not found: ${cteReceiveDto.shipFromLocationId}")

        val shipToLocation = locationService.findById(cteReceiveDto.shipToLocationId)
            ?: throw EntityNotFoundException("ShipToLocation not found: ${cteReceiveDto.shipToLocationId}")

        val tlcSource = locationService.findById(cteReceiveDto.tlcSourceId)
            ?: throw EntityNotFoundException("TlcSource not found: ${cteReceiveDto.tlcSourceId}")

        val cteReceive =
            cteReceiveDto.toCteReceive(cteBusName, traceLotCode, shipFromLocation, shipToLocation, tlcSource)
        val cteReceiveResponse = cteReceiveService.insert(cteReceive).toCteReceiveDto()
        return ResponseEntity.created(URI.create(CTE_RECEIVE_BASE_URL.plus("/${cteReceiveResponse.id}")))
            .body(cteReceiveResponse)
    }

    // -- Update an existing Location
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody cteReceiveDto: CteReceiveDto,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<CteReceiveDto> {
        if (cteReceiveDto.id <= 0L || cteReceiveDto.id != id)
            throw UnauthorizedRequestException("Conflicting CteReceive Ids specified: $id != ${cteReceiveDto.id}")

        val cteBusName = foodBusService.findById(cteReceiveDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteReceiveDto.cteBusNameId}")

        val traceLotCode = traceLotCodeService.findById(cteReceiveDto.tlcId)
            ?: throw EntityNotFoundException("TraceLotCode not found: ${cteReceiveDto.tlcId}")

        val shipFromLocation = locationService.findById(cteReceiveDto.shipFromLocationId)
            ?: throw EntityNotFoundException("ShipFromLocation not found: ${cteReceiveDto.shipFromLocationId}")

        val shipToLocation = locationService.findById(cteReceiveDto.shipToLocationId)
            ?: throw EntityNotFoundException("ShipToLocation not found: ${cteReceiveDto.shipToLocationId}")

        val tlcSource = locationService.findById(cteReceiveDto.tlcSourceId)
            ?: throw EntityNotFoundException("TlcSource not found: ${cteReceiveDto.tlcSourceId}")

        val cteReceive =
            cteReceiveDto.toCteReceive(cteBusName, traceLotCode, shipFromLocation, shipToLocation, tlcSource)
        val cteReceiveCto = cteReceiveService.update(cteReceive).toCteReceiveDto()
        return ResponseEntity.ok().body(cteReceiveCto)
    }

    // -- Delete an existing Address
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
        @AuthenticationPrincipal authPrincipal: FsmaUser
    ): ResponseEntity<Void> {
        cteReceiveService.findById(id)?.let { ctcCoolCto ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            cteReceiveService.delete(ctcCoolCto) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}