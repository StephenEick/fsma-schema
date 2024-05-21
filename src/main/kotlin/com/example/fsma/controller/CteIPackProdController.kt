package com.example.fsma.controller

import com.example.fsma.model.cte.CteIPackProdDto
import com.example.fsma.model.cte.toCteIPackProd
import com.example.fsma.model.cte.toCteIPackProdDto
import com.example.fsma.util.EntityNotFoundException
import com.example.fsma.util.UnauthorizedRequestException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val CTE_IPACK_PROD_BASE_URL = "/api/v1/cteipackprod"
private const val CTE_IPACK_PROD_ALT_BASE_URL = "/api/v1/cte-ipack-prod"
private const val CTE_IPACK_PROD_ALT2_BASE_URL = "/api/v1/cteipp"

@RestController
@RequestMapping(value = [CTE_IPACK_PROD_BASE_URL, CTE_IPACK_PROD_ALT_BASE_URL, CTE_IPACK_PROD_ALT2_BASE_URL])
//@SecurityRequirement(name = "bearerAuth")
class CteIPackProdController : BaseController() {

    // -- Return a specific CteCool
    // -    http://localhost:8080/api/v1/addresses/1
    @GetMapping("/{id}")
    fun findById(
        @PathVariable(value = "id") id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteIPackProdDto> {
        val cteIPackProd = cteIPackProdService.findById(id)
            ?: throw EntityNotFoundException("CteIPackProd not found = $id")
//        assertResellerClientMatchesToken(fsaUser, address.resellerId)
        return ResponseEntity.ok(cteIPackProd.toCteIPackProdDto())
    }

    // -- Create a new Address
    @PostMapping
    fun create(
        @Valid @RequestBody cteIPackProdDto: CteIPackProdDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteIPackProdDto> {
        val cteBusName = businessService.findById(cteIPackProdDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteIPackProdDto.cteBusNameId}")

        val harvestLocation = locationService.findById(cteIPackProdDto.harvestLocationId)
            ?: throw EntityNotFoundException("HarvestLocation not found: ${cteIPackProdDto.harvestLocationId}")

        val harvestBusiness = businessService.findById(cteIPackProdDto.harvestBusinessId)
            ?: throw EntityNotFoundException("HarvestBusiness not found: ${cteIPackProdDto.harvestBusinessId}")

        val coolLocation = cteIPackProdDto.coolLocationId?.let {
            locationService.findById(it) ?: throw EntityNotFoundException("CoolLocation not found: $it")
        }

        val packTlc = traceLotCodeService.findById(cteIPackProdDto.packTlcId)
            ?: throw EntityNotFoundException("PackTlc not found: ${cteIPackProdDto.packTlcId}")

        val packTlcSource = cteIPackProdDto.packTlcSourceId?.let {
            locationService.findById(it) ?: throw EntityNotFoundException("PackTlcSource not found: $it")
        }

        val cteIPackProd = cteIPackProdDto.toCteIPackProd(
            cteBusName, harvestLocation, harvestBusiness, coolLocation, packTlc, packTlcSource
        )
        val cteIPackProdDto = cteIPackProdService.insert(cteIPackProd).toCteIPackProdDto()
        return ResponseEntity.created(URI.create(CTE_IPACK_PROD_BASE_URL.plus("/${cteIPackProdDto.id}")))
            .body(cteIPackProdDto)
    }

    // -- Update an existing Location
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody cteIPackProdDto: CteIPackProdDto,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<CteIPackProdDto> {
        if (cteIPackProdDto.id <= 0L || cteIPackProdDto.id != id)
            throw UnauthorizedRequestException("Conflicting CteIPackProd Ids specified: $id != ${cteIPackProdDto.id}")

        val cteBusName = businessService.findById(cteIPackProdDto.cteBusNameId)
            ?: throw EntityNotFoundException("CteBusName not found: ${cteIPackProdDto.cteBusNameId}")

        val harvestLocation = locationService.findById(cteIPackProdDto.harvestLocationId)
            ?: throw EntityNotFoundException("HarvestLocation not found: ${cteIPackProdDto.harvestLocationId}")

        val harvestBusiness = businessService.findById(cteIPackProdDto.harvestBusinessId)
            ?: throw EntityNotFoundException("HarvestBusiness not found: ${cteIPackProdDto.harvestBusinessId}")

        val coolLocation = cteIPackProdDto.coolLocationId?.let {
            locationService.findById(it) ?: throw EntityNotFoundException("CoolLocation not found: $it")
        }

        val packTlc = traceLotCodeService.findById(cteIPackProdDto.packTlcId)
            ?: throw EntityNotFoundException("PackTlc not found: ${cteIPackProdDto.packTlcId}")

        val packTlcSource = cteIPackProdDto.packTlcSourceId?.let {
            locationService.findById(it) ?: throw EntityNotFoundException("PackTlcSource not found: $it")
        }

        val cteIPackProd = cteIPackProdDto.toCteIPackProd(
            cteBusName, harvestLocation, harvestBusiness, coolLocation, packTlc, packTlcSource
        )
        val cteIPackProdCto = cteIPackProdService.update(cteIPackProd).toCteIPackProdDto()
        return ResponseEntity.ok().body(cteIPackProdCto)
    }

    // -- Delete an existing Address
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long,
//        @AuthenticationPrincipal fsaUser: FsaUser
    ): ResponseEntity<Void> {
        cteIPackProdService.findById(id)?.let { ctcCoolCto ->
//            assertResellerClientMatchesToken(fsaUser, address.resellerId)
            cteIPackProdService.delete(ctcCoolCto) // soft delete?
        }
        return ResponseEntity.noContent().build()
    }
}