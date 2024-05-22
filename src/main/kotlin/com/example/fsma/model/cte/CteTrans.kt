package com.example.fsma.model.cte

import com.example.fsma.model.FoodBusiness
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
look at p.29

https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1350
§ 1.1350 What records must I keep when I transform a food on the
Food Traceability List?
 **/

@Entity
data class CteTrans(
    @Id @GeneratedValue override val id: Long = 0,

    override val cteType: CteType = CteType.Transform,

    // Packer business name for the creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val cteBusName: FoodBusiness,

    @Enumerated(EnumType.STRING)
    val foodItem: FtlItem,
    val variety: String,

    // ************** KDEs *************
    // (a) Except as specified in paragraphs (b) and (c) of this section,
    // for each new traceability lot of food you produce through transformation
    // you must maintain records containing the following information
    // and linking this information to the new traceability lot:

    // (a)(1) For the food on the Food Traceability List used in transformation (if applicable),
    // the following information:

    // (a)(1)(i) The traceability lot code for the food;
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val inputTlc: TraceLotCode,  // from Initial Packer or previous Transformer

    // (a)(1)(ii) The product description for the food to which the traceability
    // lot code applies; and
    val inputFoodDesc: String, // from Initial Packer or previous Transformer

    // (a)(1)(iii) For each traceability lot used, the quantity and unit of measure
    // of the food used from that lot.
    val inputQuantity: Double,   // from Initial Packer
    val inputUnitOfMeasure: UnitOfMeasure,   // from Initial Packer

    // (a)(2) For the food produced through transformation, the following
    // information:

    // (a)(2)(i) The new traceability lot code for the food;
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val newTlc: TraceLotCode,  // the new Tlc

    // (a)(2)(ii) The location description for where you transformed
    // the food (i.e., the traceability lot code source),
    // and (if applicable) the traceability lot code source reference;
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val newTlcLocation: Location,
    val newTlcSourceReference: String? = null,

    // (a)(2)(iii) The date transformation was completed;
    val transDate: LocalDate,

    // (a)(2)(iv) The product description for the food;
    val newFoodDesc: String,

    // For the transformed food
    // (a)(2)(v) The quantity and unit of measure of the
    // food (e.g., 6 cases, 25 reusable plastic containers,
    // 100 tanks, 200 pounds); and
    override val quantity: Double,
    override val unitOfMeasure: UnitOfMeasure,

    // (a)(2)(vi) The reference document type and reference document
    // number for the transformation event.
    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null,

    // (b) For each traceability lot produced through transformation of a raw
    // agricultural commodity (other than a food obtained from a fishing vessel)
    // on the Food Traceability List that was not initially packed prior to your
    // transformation of the food, you must maintain records containing the
    // information specified in § 1.1330(a) or (c), and, if the raw agricultural
    // commodity is sprouts, the information specified in § 1.1330(b).

    // (c) Paragraphs (a) and (b) of this section do not apply to retail food
    // establishments and restaurants with respect to foods they do not ship
    // (e.g., foods they sell or send directly to consumers).

) : CteBase<CteTrans>()

data class CteTransDto(
    val id: Long,
    val cteType: CteType,
    val cteBusNameId: Long,
    val foodItem: FtlItem,
    val variety: String,
    val inputTlcId: Long,  // from Initial Packer or previous Transformer
    val inputFoodDesc: String, // from Initial Packer or previous Transformer
    val inputQuantity: Double,   // from Initial Packer
    val inputUnitOfMeasure: UnitOfMeasure,   // from Initial Packer
    val newTlcId: Long,  // the new Tlc
    val newTlcLocationId: Long,
    val newTlcSourceReference: String? = null,
    val transDate: LocalDate,
    val newFoodDesc: String,
    val quantity: Double,
    val unitOfMeasure: UnitOfMeasure,
    val referenceDocumentType: ReferenceDocumentType,
    val referenceDocumentNum: String,
    val dateCreated: OffsetDateTime,
    val dateModified: OffsetDateTime,
    val isDeleted: Boolean,
    val dateDeleted: OffsetDateTime?,
)

fun CteTrans.toCteTransDto() = CteTransDto(
    id = id,
    cteType = cteType,
    cteBusNameId = cteBusName.id,
    foodItem = foodItem,
    variety = variety,
    inputTlcId = inputTlc.id,  // from Initial Packer or previous Transformer
    inputFoodDesc = inputFoodDesc, // from Initial Packer or previous Transformer
    inputQuantity = inputQuantity,   // from Initial Packer
    inputUnitOfMeasure = inputUnitOfMeasure,   // from Initial Packer
    newTlcId = newTlc.id,  // the new Tlc after transformation
    newTlcLocationId = newTlcLocation.id,
    newTlcSourceReference = newTlcSourceReference,
    transDate = transDate,
    newFoodDesc = newFoodDesc,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    referenceDocumentType = referenceDocumentType,
    referenceDocumentNum = referenceDocumentNum,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun CteTransDto.toCteTrans(
    cteBusName: FoodBusiness,
    inputTlc: TraceLotCode,
    newTlc: TraceLotCode,
    newTlcLocation: Location,
) = CteTrans(
    id = id,
    cteType = cteType,
    cteBusName = cteBusName,
    foodItem = foodItem,
    variety = variety,
    inputTlc = inputTlc,  // from Initial Packer or previous Transformer
    inputFoodDesc = inputFoodDesc, // from Initial Packer or previous Transformer
    inputQuantity = inputQuantity,   // from Initial Packer
    inputUnitOfMeasure = inputUnitOfMeasure,   // from Initial Packer
    newTlc = newTlc,  // the new Tlc
    newTlcLocation = newTlcLocation,
    newTlcSourceReference = newTlcSourceReference,
    transDate = transDate,
    newFoodDesc = newFoodDesc,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    referenceDocumentType = referenceDocumentType,
    referenceDocumentNum = referenceDocumentNum,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)