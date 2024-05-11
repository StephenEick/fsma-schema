package com.example.fsma.model.cte

import com.example.fsma.model.Business
import com.example.fsma.model.Location
import com.example.fsma.util.CteType
import com.example.fsma.util.FtlItem
import com.example.fsma.util.ReferenceDocumentType
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime

/**
https://producetraceability.org/wp-content/uploads/2024/02/PTI-FSMA-204-Implementation-Guidance-FINAL-2.12.24.pdf
Look at p.20

https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1325#p-1.1325(a)
§ 1.1325 What records must I keep and provide when I harvest or cool
a raw agricultural commodity on the Food Traceability List?
 **/

// (a) Harvesting.
@Entity
data class CteHarvest(
    @Id @GeneratedValue override val id: Long = 0,

    @Enumerated(EnumType.STRING)
    override val cteType: CteType = CteType.Harvest,

    // ************** KDEs *************
    // (1) For each raw agricultural commodity (not obtained from a fishing vessel)
    // on the Food Traceability List that you harvest, you must maintain records
    // containing the following information:

    // (1)(i) The location description for the immediate subsequent recipient
    // (other than a transporter) of the food;
    @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn
    val subsequentRecipient: Location,

    // (1)(ii) The commodity and, if applicable, variety of the food;
    @Enumerated(EnumType.STRING)
    val commodity: FtlItem,
    val commodityVariety: String,

    // (1)(iii) The quantity and unit of measure of the food (e.g., 75 bins, 200 pounds);
    val harvestQuantity: Double,
    @Enumerated(EnumType.STRING)
    val harvestUnitOfMeasure: UnitOfMeasure,

    // (1)(iv) The location description for the farm where the food was harvested;
    @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn
    val harvestLocation: Location,

    // (1)(v) For produce, the name of the field or other growing area from which the
    // food was harvested (which must correspond to the name used by the grower),
    // or other information identifying the harvest location at least as precisely
    // as the field or other growing area name;
    val fieldName: String,
    val fieldDesc: String,

    // (1)(vi) For aquacultured food, the name of the container
    // (e.g., pond, pool, tank, cage) from which the food was harvested
    // (which must correspond to the container name
    // used by the aquaculture farmer) or other information identifying the harvest
    // location at least as precisely as the container name;
    val containerName: String? = null,
    val containerDesc: String? = null,

    // (1)(vii) The date of harvesting;
    val harvestDate: LocalDate,

    // (1)(viii) The reference document type and reference document number.
    @Enumerated(EnumType.STRING)
    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,

    // (2) For each raw agricultural commodity (not obtained from a fishing vessel)
    // on the Food Traceability List that you harvest, you must provide (in electronic,
    // paper, or other written form) your business name, phone number, and the
    // information in paragraphs (a)(1)(i) through (vii) of this section to the
    // initial packer of the raw agricultural commodity you harvest, either directly
    // or through the supply chain.

    // Harvest business name, e.g. creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn
    override val cteBusName: Business,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null
) : CteBase<CteHarvest>()

data class CteHarvestDto(
    val id: Long,
    val cteType: CteType,
    val subsequentRecipientId: Long,
    val commodity: FtlItem,
    val commodityVariety: String,
    val harvestQuantity: Double,
    val harvestUnitOfMeasure: UnitOfMeasure,
    val harvestLocationId: Long,
    val fieldName: String,
    val fieldDesc: String,
    val containerName: String?,
    val containerDesc: String?,
    val harvestDate: LocalDate,
    val referenceDocumentType: ReferenceDocumentType,
    val referenceDocumentNum: String,
    val cteBusNameId: Long,
    val dateCreated: OffsetDateTime,
    val dateModified: OffsetDateTime,
    val isDeleted: Boolean,
    val dateDeleted: OffsetDateTime?,
)

fun CteHarvest.toCteHarvestDto() = CteHarvestDto(
    id = id,
    cteType = cteType,
    subsequentRecipientId = subsequentRecipient.id,
    commodity = commodity,
    commodityVariety = commodityVariety,
    harvestQuantity = harvestQuantity,
    harvestUnitOfMeasure = harvestUnitOfMeasure,
    harvestLocationId = harvestLocation.id,
    fieldName = fieldName,
    fieldDesc = fieldDesc,
    containerName = containerName,
    containerDesc = containerDesc,
    harvestDate = harvestDate,
    referenceDocumentType = referenceDocumentType,
    referenceDocumentNum = referenceDocumentNum,
    cteBusNameId = cteBusName.id,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun CteHarvestDto.toCteHarvest(
    subsequentRecipient: Location,
    harvestLocation: Location,
    cteBusName: Business,
) = CteHarvest(
    id = id,
    cteType = cteType,
    subsequentRecipient = subsequentRecipient,
    commodity = commodity,
    commodityVariety = commodityVariety,
    harvestQuantity = harvestQuantity,
    harvestUnitOfMeasure = harvestUnitOfMeasure,
    harvestLocation = harvestLocation,
    fieldName = fieldName,
    fieldDesc = fieldDesc,
    containerName = containerName,
    containerDesc = containerDesc,
    harvestDate = harvestDate,
    referenceDocumentType = referenceDocumentType,
    referenceDocumentNum = referenceDocumentNum,
    cteBusName = cteBusName,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)