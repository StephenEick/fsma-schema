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
Look at p.21

https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1325#p-1.1325(b)
§ 1.1325 What records must I keep and provide when I harvest or cool
a raw agricultural commodity on the Food Traceability List?
 **/

// (b) Cooling before initial packing.
@Entity
data class CteCool(
    @Id @GeneratedValue override val id: Long = 0,

    @Enumerated(EnumType.STRING)
    override val cteType: CteType = CteType.Cool,

    // ************** KDEs *************
    // (1) For each raw agricultural commodity
    // (not obtained from a fishing vessel) on the Food Traceability List
    // that you cool before it is initially packed, you must maintain records
    // containing the following information:

    // (1)(i) The location description for the immediate subsequent
    // recipient (other than a transporter) of the food;
    @ManyToOne @JoinColumn
    val subsequentRecipient: Location,

    // (1)(ii) The commodity and, if applicable, variety of the food;
    @Enumerated(EnumType.STRING)
    override val foodItem: FtlItem,
    override val variety: String,
    override val foodDesc: String,

    // (1)(iii) The quantity and unit of measure of the food (e.g., 75 bins, 200 pounds);
    override val quantity: Double,
    override val unitOfMeasure: UnitOfMeasure,

    // (1)(iv) The location description for where you cooled the food;
    @ManyToOne @JoinColumn
    val coolLocation: Location,
    @Enumerated(EnumType.STRING)
    val coolUnitOfMeasure: UnitOfMeasure,

    // (1)(v) The date of cooling;
    val coolDate: LocalDate,

    // (1)(vi) The location description for the farm where the food was harvested;
    @ManyToOne @JoinColumn
    val harvestLocation: Location,

    // (1)(vii) The reference document type and reference document number.
    @Enumerated(EnumType.STRING)
    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,

    // (2) For each raw agricultural commodity (not obtained from a fishing vessel)
    // on the Food Traceability List that you harvest, you must provide (in electronic,
    // paper, or other written form) your business name, phone number, and the
    // information in paragraphs (a)(1)(i) through (vii) of this section to the
    // initial packer of the raw agricultural commodity you harvest, either directly
    // or through the supply chain.

    // Cooler business name
    @ManyToOne  @JoinColumn
    override val cteBusName: Business,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null
) : CteBase<CteCool>()

data class CteCoolDto(
    val id: Long,
    val cteType: CteType = CteType.Cool,
    val subsequentRecipientId: Long,
    val foodItem: FtlItem,
    val variety: String,
    val foodDesc: String,
    val quantity: Double,
    val unitOfMeasure: UnitOfMeasure,
    val coolLocation: Location,
    val coolUnitOfMeasure: UnitOfMeasure,
    val coolDate: LocalDate,
    val harvestLocation: Location,
    val referenceDocumentType: ReferenceDocumentType,
    val referenceDocumentNum: String,
    val cteBusNameId: Long,
    val dateCreated: OffsetDateTime,
    val dateModified: OffsetDateTime,
    val isDeleted: Boolean,
    val dateDeleted: OffsetDateTime?,
)

fun CteCool.toCteCoolDto() = CteCoolDto(
    id = id,
    cteType = cteType,
    subsequentRecipientId = subsequentRecipient.id,
    foodItem = foodItem,
    variety = variety,
    foodDesc = foodDesc,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    coolLocation = coolLocation,
    coolUnitOfMeasure = coolUnitOfMeasure,
    coolDate = coolDate,
    harvestLocation = harvestLocation,
    referenceDocumentType = referenceDocumentType,
    referenceDocumentNum = referenceDocumentNum,
    cteBusNameId = cteBusName.id,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun CteCoolDto.toCteCool(
    subsequentRecipient: Location,
    cteBusName: Business,
) = CteCool(
    id = id,
    cteType = cteType,
    subsequentRecipient = subsequentRecipient,
    foodItem = foodItem,
    variety = variety,
    foodDesc = foodDesc,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    coolLocation = coolLocation,
    coolUnitOfMeasure = coolUnitOfMeasure,
    coolDate = coolDate,
    harvestLocation = harvestLocation,
    referenceDocumentType = referenceDocumentType,
    referenceDocumentNum = referenceDocumentNum,
    cteBusName = cteBusName,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)