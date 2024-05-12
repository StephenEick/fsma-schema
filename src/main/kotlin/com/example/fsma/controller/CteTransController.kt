package com.example.fsma.controller

import com.example.fsma.model.cte.CteTransDto
import com.example.fsma.model.cte.toCteTrans
import com.example.fsma.model.cte.toCteTransDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val CTE_TRANSFORM_BASE_URL = "/api/v1/ctetransform"
private const val CTE_TRANSFORM_ALT_BASE_URL = "/api/v1/cte-transform"

@RestController
@RequestMapping(value = [CTE_TRANSFORM_BASE_URL, CTE_TRANSFORM_ALT_BASE_URL])
//@SecurityRequirement(name = "bearerAuth")
class CteTransController : BaseController() {

    // -- Return a specific CteCool
    // -    http://localhost:8080/api/v1/addresses/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteTransDto> {
        val cteTransform = cteTransformService.findById(id)
            ?: throw EntityNotFoundException("CteTransform not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(cteTransform.toCteTransDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody cteTransDto: CteTransDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteTransDto> {
        val cteBusName = businessService.findById(cteTransDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteTransDto.cteBusNameId}")

        val traceLotCode = traceLotCodeService.findById(cteTransDto.usedTlcId)
            ?: throw EntityNotFoundException("TraceLotCode not found: ${cteTransDto.usedTlcId}")

        val transformLotCode = traceLotCodeService.findById(cteTransDto.transTlcId)
            ?: throw EntityNotFoundException("TraceLotCode not found: ${cteTransDto.usedTlcId}")

        val transformFromLocation = locationService.findById(cteTransDto.transTlcLocationId)
            ?: throw EntityNotFoundException("TransformFromLocation not found: ${cteTransDto.transTlcLocationId}")

        val cteTransform = cteTransDto.toCteTrans(cteBusName, traceLotCode, transformLotCode, transformFromLocation)
        val cteTransformResponse = cteTransformService.insert(cteTransform).toCteTransDto()
        return ResponseEntity.created(URI.create(CTE_TRANSFORM_BASE_URL.plus("/${cteTransformResponse.id}")))
            .body(cteTransformResponse)
    }

    // -- Update an existing Location
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody cteTransDto: CteTransDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteTransDto> {
        if (cteTransDto.id <= 0L || cteTransDto.id != id)
            throw UnauthorizedRequestException("Conflicting CteTransform Ids specified: $id != ${cteTransDto.id}")

        val cteBusName = businessService.findById(cteTransDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteTransDto.cteBusNameId}")

        val traceLotCode = traceLotCodeService.findById(cteTransDto.usedTlcId)
            ?: throw EntityNotFoundException("TraceLotCode not found: ${cteTransDto.usedTlcId}")

        val transformLotCode = traceLotCodeService.findById(cteTransDto.transTlcId)
            ?: throw EntityNotFoundException("TraceLotCode not found: ${cteTransDto.usedTlcId}")

        val transformFromLocation = locationService.findById(cteTransDto.transTlcLocationId)
            ?: throw EntityNotFoundException("TransformFromLocation not found: ${cteTransDto.transTlcLocationId}")

        val cteTransform = cteTransDto.toCteTrans(cteBusName, traceLotCode, transformLotCode, transformFromLocation)

        val cteTransformCto = cteTransformService.update(cteTransform).toCteTransDto()
        return ResponseEntity.ok().body(cteTransformCto)
    }

    // -- Delete an existing Address
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        cteTransformService.findById(id)?.let { ctcCoolCto ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            cteTransformService.delete(ctcCoolCto) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}