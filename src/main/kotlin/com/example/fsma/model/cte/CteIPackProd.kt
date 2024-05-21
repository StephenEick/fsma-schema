package com.example.fsma.model.cte

import com.example.fsma.model.Business
import com.example.fsma.model.Location
import com.example.fsma.model.TraceLotCode
import com.example.fsma.util.CteType
import com.example.fsma.util.FtlItem
import com.example.fsma.util.ReferenceDocumentType
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime

/**
https://producetraceability.org/wp-content/uploads/2024/02/PTI-FSMA-204-Implementation-Guidance-FINAL-2.12.24.pdf
Look at p.22

https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1330
§ 1.1330 What records must I keep when I am performing the initial packing of a raw
agricultural commodity (other than a food obtained from a fishing vessel) on the
Food Traceability List?
 **/

@Entity
@Table(name="cte_ipack_prod")
data class CteIPackProd(
    @Id @GeneratedValue override val id: Long = 0,

    @Enumerated(EnumType.STRING)
    override val cteType: CteType = CteType.InitPackProduce,

    // Pack business name, e.g. creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn
    override val cteBusName: Business,

    // ************** KDEs *************
    // (a) Except as specified in paragraph (c) of this section, for each traceability
    // lot of a raw agricultural commodity (other than a food obtained from a fishing vessel)
    // on the Food Traceability List you initially pack, you must maintain records
    // containing the following information and linking this information to the
    // traceability lot:

    // (a)(1) The commodity and, if applicable, variety of the food;
    @Enumerated(EnumType.STRING)
    val commodity: FtlItem, // or commodity
    val variety: String,

    // (a)(2) The date you received the food;
    val receiveDate: LocalDate,

    // (a)(3) The quantity and unit of measure of the food received (e.g., 75 bins, 200 pounds);
    val receiveQuantity: Double,
    @Enumerated(EnumType.STRING)
    val receiveUnitOfMeasure: UnitOfMeasure,

    // (a)(4) The location description for the farm where the food was harvested;
    @ManyToOne @JoinColumn
    val harvestLocation: Location,

    // (a)(5) For produce, the name of the field or other growing area from which the
    // food was harvested (which must correspond to the name used by the grower),
    // or other information identifying the harvest location at least as precisely
    // as the field or other growing area name;
    val fieldName: String,
    val fieldDesc: String,

    // (a)(6) For aquacultured food, the name of the container
    // (e.g., pond, pool, tank, cage) from which the food was harvested
    // (which must correspond to the container name
    // used by the aquaculture farmer) or other information identifying the harvest
    // location at least as precisely as the container name;
    val containerName: String,
    val containerDesc: String,

    // (a)(7) The business name and phone number for the harvester of the food
    @ManyToOne @JoinColumn
    val harvestBusiness: Business,

    // (a)(8) The date of harvesting;
    val harvestDate: LocalDate,

    // (a)(9) The location description for where the food was cooled
    // (if applicable);
    @ManyToOne @JoinColumn
    val coolLocation: Location? = null,

    // (a)(10) The date of cooling (if applicable);
    val coolDate: LocalDate? = null,

    // (a)(11) The traceability lot code you assigned;
    @ManyToOne @JoinColumn
    val packTlc: TraceLotCode,

    // (a)(12) The product description of the packed food;
    val packFoodDesc: String,

    // Quantity packed and pack unit of measure.
    // (a)(13) The quantity and unit of measure of the packed food (e.g., 6 cases, 25 reusable plastic containers, 100 tanks, 200 pounds);
    override val quantity: Double,
    @Enumerated(EnumType.STRING)
    override val unitOfMeasure: UnitOfMeasure,

    // (a)(14) The location description for where you initially packed the food
    // (i.e., the traceability lot code source), and (if applicable) the traceability
    // lot code source reference;
    // Either the tlcSource or the tlcSourceReference should be null.
    // Only one of these should be populated in production
    @ManyToOne @JoinColumn
    val packTlcSource: Location? = null,    // i.e., the packLocation since TLC is created at this CTE
    val packTlcSourceReference: String? = null,

    // (a)(15) The date of initial packing; and
    val packDate: LocalDate,

    // (a)(16) The reference document type and reference document number.
    @Enumerated(EnumType.STRING)
    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null
) : CteBase<CteIPackProd>()

data class CteIPackProdDto(
    val id: Long,
    val cteType: CteType,
    val cteBusNameId: Long,
    val commodity: FtlItem,
    val variety: String,
    val receiveDate: LocalDate,
    val receiveQuantity: Double,
    val receiveUnitOfMeasure: UnitOfMeasure,
    val harvestLocationId: Long,
    val fieldName: String,
    val fieldDesc: String,
    val containerName: String,
    val containerDesc: String,
    val harvestBusinessId: Long,
    val harvestDate: LocalDate,
    val coolLocationId: Long?,
    val coolDate: LocalDate?,
    val packTlcId: Long,
    val packFoodDesc: String,
    val quantity: Double,
    val unitOfMeasure: UnitOfMeasure,
    val packTlcSourceId: Long?,
    val packTlcSourceReference: String? = null,
    val packDate: LocalDate,
    val referenceDocumentType: ReferenceDocumentType,
    val referenceDocumentNum: String,
    val dateCreated: OffsetDateTime,
    val dateModified: OffsetDateTime,
    val isDeleted: Boolean,
    val dateDeleted: OffsetDateTime?,
)

fun CteIPackProd.toCteIPackProdDto() = CteIPackProdDto(
    id = id,
    cteType = cteType,
    cteBusNameId = cteBusName.id,
    commodity = commodity,
    variety = variety,
    receiveDate = receiveDate,
    receiveQuantity = receiveQuantity,
    receiveUnitOfMeasure = receiveUnitOfMeasure,
    harvestLocationId = harvestLocation.id,
    fieldName = fieldName,
    fieldDesc = fieldDesc,
    containerName = containerName,
    containerDesc = containerDesc,
    harvestBusinessId = harvestBusiness.id,
    harvestDate = harvestDate,
    coolLocationId = coolLocation?.id,
    coolDate = coolDate,
    packTlcId = packTlc.id,
    packFoodDesc = packFoodDesc,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    packTlcSourceId = packTlcSource?.id,
    packTlcSourceReference = packTlcSourceReference,
    packDate = packDate,
    referenceDocumentType = referenceDocumentType,
    referenceDocumentNum = referenceDocumentNum,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun CteIPackProdDto.toCteIPackProd(
    cteBusName : Business,
    harvestLocation: Location,
    harvestBusiness: Business,
    coolLocation: Location?,
    packTlc: TraceLotCode,
    packTlcSource: Location?,
) = CteIPackProd(
    id = id,
    cteType = cteType,
    cteBusName = cteBusName,
    commodity = commodity,
    variety = variety,
    receiveDate = receiveDate,
    receiveQuantity = receiveQuantity,
    receiveUnitOfMeasure = receiveUnitOfMeasure,
    harvestLocation = harvestLocation,
    fieldName = fieldName,
    fieldDesc = fieldDesc,
    containerName = containerName,
    containerDesc = containerDesc,
    harvestBusiness = harvestBusiness,
    harvestDate = harvestDate,
    coolLocation = coolLocation,
    coolDate = coolDate,
    packTlc = packTlc,
    packFoodDesc = packFoodDesc,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    packTlcSource = packTlcSource,
    packTlcSourceReference = packTlcSourceReference,
    packDate = packDate,
    referenceDocumentType = referenceDocumentType,
    referenceDocumentNum = referenceDocumentNum,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)