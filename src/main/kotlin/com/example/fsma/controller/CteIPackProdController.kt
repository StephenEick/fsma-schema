package com.example.fsma.controller

//import com.example.fsma.model.cte.CteIPackProdDto
//import com.example.fsma.model.cte.toCteIPackProd
//import com.example.fsma.model.cte.toCteIPackProdDto
//import com.example.fsma.util.EntityNotFoundException
//import com.example.fsma.util.UnauthorizedRequestException
//import jakarta.validation.Valid
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//import java.net.URI
//
//private const val CTE_IPACK_PROD_BASE_URL = "/api/v1/cteipackprod"
//private const val CTE_IPACK_PROD_ALT_BASE_URL = "/api/v1/cte-ipack-prod"
//
//@RestController
//@RequestMapping(value = [CTE_IPACK_PROD_BASE_URL, CTE_IPACK_PROD_ALT_BASE_URL])
////@SecurityRequirement(name = "bearerAuth")
//class CteIPackProdController : BaseController() {
//
//    // -- Return a specific CteCool
//    // -    http://localhost:8080/api/v1/addresses/1
//    @GetMapping("/{id}")
//    fun findById(
//        @PathVariable(value = "id") id: Long,
////        @AuthenticationPrincipal fsaUser: FsaUser
//    ): ResponseEntity<CteIPackProdDto> {
//        val cteIPackProd = cteIPackProdService.findById(id)
//            ?: throw EntityNotFoundException("CteIPackProd not found = $id")
////        assertResellerClientMatchesToken(fsaUser, address.resellerId)
//        return ResponseEntity.ok(cteIPackProd.toCteIPackProdDto())
//    }
//
//    // -- Create a new Address
//    @PostMapping
//    fun create(
//        @Valid @RequestBody cteIPackProdDto: CteIPackProdDto,
////        @AuthenticationPrincipal fsaUser: FsaUser
//    ): ResponseEntity<CteIPackProdDto> {
//        val subsequentRecipient = locationService.findById(cteIPackProdDto.subsequentRecipientId)
//            ?: throw EntityNotFoundException("SubsequentRecipient not found: ${cteIPackProdDto.subsequentRecipientId}")
//
//        val harvestLocation = locationService.findById(cteIPackProdDto.harvestLocationId)
//            ?: throw EntityNotFoundException("HarvestLocation not found: ${cteIPackProdDto.harvestLocationId}")
//
//        val cteBusName = businessService.findById(cteIPackProdDto.cteBusNameId)
//            ?: throw EntityNotFoundException("CteBusName not found: ${cteIPackProdDto.cteBusNameId}")
//
//        val cteIPackProd = cteIPackProdDto.toCteIPackProd(subsequentRecipient, harvestLocation, cteBusName)
//        val cteIPackProdResponse = cteIPackProdService.insert(cteIPackProd).toCteIPackProdDto()
//        return ResponseEntity.created(URI.create(CTE_HARVEST_BASE_URL.plus("/${cteIPackProdResponse.id}")))
//            .body(cteIPackProdResponse)
//    }
//
//    // -- Update an existing Location
//    @PutMapping("/{id}")
//    fun update(
//        @PathVariable id: Long,
//        @Valid @RequestBody cteIPackProdDto: CteIPackProdDto,
////        @AuthenticationPrincipal fsaUser: FsaUser
//    ): ResponseEntity<CteIPackProdDto> {
//        if (cteIPackProdDto.id <= 0L || cteIPackProdDto.id != id)
//            throw UnauthorizedRequestException("Conflicting CteIPackProd Ids specified: $id != ${cteIPackProdDto.id}")
//
//        val subsequentRecipient = locationService.findById(cteIPackProdDto.subsequentRecipientId)
//            ?: throw EntityNotFoundException("SubsequentRecipient Location not found: ${cteIPackProdDto.subsequentRecipientId}")
//
//        val harvestLocation = locationService.findById(cteIPackProdDto.harvestLocationId)
//            ?: throw EntityNotFoundException("HarvestLocation not found: ${cteIPackProdDto.harvestLocationId}")
//
//        val cteBusName = businessService.findById(cteIPackProdDto.cteBusNameId)
//            ?: throw EntityNotFoundException("CteBusName Business not found: ${cteIPackProdDto.cteBusNameId}")
//
//        val cteIPackProd = cteIPackProdDto.toCteIPackProd(cteBusName,harvestLocation)
//        val cteIPackProdCto = cteIPackProdService.update(cteIPackProd).toCteIPackProdDto()
//        return ResponseEntity.ok().body(cteIPackProdCto)
//    }
//
//    // -- Delete an existing Address
//    @DeleteMapping("/{id}")
//    fun deleteById(
//        @PathVariable id: Long,
////        @AuthenticationPrincipal fsaUser: FsaUser
//    ): ResponseEntity<Void> {
//        cteIPackProdService.findById(id)?.let { ctcCoolCto ->
////            assertResellerClientMatchesToken(fsaUser, address.resellerId)
//            cteIPackProdService.delete(ctcCoolCto) // soft delete?
//        }
//        return ResponseEntity.noContent().build()
//    }
//}