package com.example.fsma.controller

import com.example.fsma.model.cte.CteHarvestDto
import com.example.fsma.model.cte.toCteHarvest
import com.example.fsma.model.cte.toCteHarvestDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val CTE_HARVEST_BASE_URL = "/api/v1/cteharvest"
private const val CTE_HARVEST_ALT_BASE_URL = "/api/v1/cte-harvest"

@RestController
@RequestMapping(value = [CTE_HARVEST_BASE_URL, CTE_HARVEST_ALT_BASE_URL])
//@SecurityRequirement(name = "bearerAuth")
class CteHarvestController : BaseController() {

    // -- Return a specific CteCool
    // -    http://localhost:8080/api/v1/addresses/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteHarvestDto> {
        val cteHarvest = cteHarvestService.findById(id)
            ?: throw EntityNotFoundException("CteHarvest not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(cteHarvest.toCteHarvestDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody cteHarvestDto: CteHarvestDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteHarvestDto> {
        val subsequentRecipient = locationService.findById(cteHarvestDto.subsequentRecipientId)
            ?: throw EntityNotFoundException("SubsequentRecipient not found: ${cteHarvestDto.subsequentRecipientId}")

        val harvestLocation = locationService.findById(cteHarvestDto.harvestLocationId)
            ?: throw EntityNotFoundException("HarvestLocation not found: ${cteHarvestDto.harvestLocationId}")

        val cteBusName = businessService.findById(cteHarvestDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteHarvestDto.cteBusNameId}")

        val cteHarvest = cteHarvestDto.toCteHarvest(subsequentRecipient, harvestLocation, cteBusName)
        val cteHarvestResponse = cteHarvestService.insert(cteHarvest).toCteHarvestDto()
        return ResponseEntity.created(URI.create(CTE_HARVEST_BASE_URL.plus("/${cteHarvestResponse.id}")))
            .body(cteHarvestResponse)
    }

    // -- Update an existing Location
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody cteHarvestDto: CteHarvestDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteHarvestDto> {
        if (cteHarvestDto.id <= 0L || cteHarvestDto.id != id)
            throw UnauthorizedRequestException("Conflicting CteHarvest Ids specified: $id != ${cteHarvestDto.id}")

        val subsequentRecipient = locationService.findById(cteHarvestDto.subsequentRecipientId)
            ?: throw EntityNotFoundException("SubsequentRecipient Location not found: ${cteHarvestDto.subsequentRecipientId}")

        val harvestLocation = locationService.findById(cteHarvestDto.harvestLocationId)
            ?: throw EntityNotFoundException("HarvestLocation not found: ${cteHarvestDto.harvestLocationId}")

        val cteBusName = businessService.findById(cteHarvestDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName Business not found: ${cteHarvestDto.cteBusNameId}")

        val cteHarvest = cteHarvestDto.toCteHarvest(subsequentRecipient, harvestLocation, cteBusName)
        val cteHarvestCto = cteHarvestService.update(cteHarvest).toCteHarvestDto()
        return ResponseEntity.ok().body(cteHarvestCto)
    }

    // -- Delete an existing Address
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        cteHarvestService.findById(id)?.let { ctcCoolCto ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            cteHarvestService.delete(ctcCoolCto) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}