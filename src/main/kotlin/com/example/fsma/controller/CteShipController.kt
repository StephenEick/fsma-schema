package com.example.fsma.controller

import com.example.fsma.model.cte.CteShipDto
import com.example.fsma.model.cte.toCteShip
import com.example.fsma.model.cte.toCteShipDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val CTE_SHIP_BASE_URL = "/api/v1/cteship"
private const val CTE_SHIP_ALT_BASE_URL = "/api/v1/cte-ship"

@RestController
@RequestMapping(value = [CTE_SHIP_BASE_URL, CTE_SHIP_ALT_BASE_URL])
//@SecurityRequirement(name = "bearerAuth")
class CteShipController : BaseController() {

    // -- Return a specific CteCool
    // -    http://localhost:8080/api/v1/addresses/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteShipDto> {
        val cteShip = cteShipService.findById(id)
            ?: throw EntityNotFoundException("CteShip not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(cteShip.toCteShipDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody cteShipDto: CteShipDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteShipDto> {
        val cteBusName = businessService.findById(cteShipDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteShipDto.cteBusNameId}")

        val traceLotCode = traceLotCodeService.findById(cteShipDto.tlcId)
            ?: throw EntityNotFoundException("TraceLotCode not found: ${cteShipDto.tlcId}")

        val shipFromLocation = locationService.findById(cteShipDto.shipFromLocationId)
            ?: throw EntityNotFoundException("ShipFromLocation not found: ${cteShipDto.shipFromLocationId}")

        val shipToLocation = locationService.findById(cteShipDto.shipToLocationId)
            ?: throw EntityNotFoundException("ShipToLocation not found: ${cteShipDto.shipToLocationId}")

        val tlcSource = locationService.findById(cteShipDto.tlcSourceId)
            ?: throw EntityNotFoundException("TlcSource not found: ${cteShipDto.tlcSourceId}")

        val cteShip = cteShipDto.toCteShip(cteBusName, traceLotCode, shipToLocation, shipFromLocation, tlcSource)
        val cteShipResponse = cteShipService.insert(cteShip).toCteShipDto()
        return ResponseEntity.created(URI.create(CTE_SHIP_BASE_URL.plus("/${cteShipResponse.id}")))
            .body(cteShipResponse)
    }

    // -- Update an existing Location
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody cteShipDto: CteShipDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteShipDto> {
        if (cteShipDto.id <= 0L || cteShipDto.id != id)
            throw UnauthorizedRequestException("Conflicting CteShip Ids specified: $id != ${cteShipDto.id}")

        val cteBusName = businessService.findById(cteShipDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteShipDto.cteBusNameId}")

        val traceLotCode = traceLotCodeService.findById(cteShipDto.tlcId)
            ?: throw EntityNotFoundException("TraceLotCode not found: ${cteShipDto.tlcId}")

        val shipFromLocation = locationService.findById(cteShipDto.shipFromLocationId)
            ?: throw EntityNotFoundException("ShipFromLocation not found: ${cteShipDto.shipFromLocationId}")

        val shipToLocation = locationService.findById(cteShipDto.shipToLocationId)
            ?: throw EntityNotFoundException("ShipToLocation not found: ${cteShipDto.shipToLocationId}")

        val tlcSource = locationService.findById(cteShipDto.tlcSourceId)
            ?: throw EntityNotFoundException("TlcSource not found: ${cteShipDto.tlcSourceId}")

        val cteShip = cteShipDto.toCteShip(cteBusName, traceLotCode, shipToLocation, shipFromLocation, tlcSource)
        val cteShipCto = cteShipService.update(cteShip).toCteShipDto()
        return ResponseEntity.ok().body(cteShipCto)
    }

    // -- Delete an existing Address
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        cteShipService.findById(id)?.let { ctcCoolCto ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            cteShipService.delete(ctcCoolCto) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}