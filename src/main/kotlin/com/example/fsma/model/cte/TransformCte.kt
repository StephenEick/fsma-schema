package com.example.fsma.model.cte

import com.example.fsma.model.BusinessName
import com.example.fsma.model.TraceabilityLotCode
import com.example.fsma.util.CteType
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1350

§ 1.1350 What records must I keep when I transform a food on the
Food Traceability List?
 **/

@Entity
data class TransformCte(
    @Id @GeneratedValue
    override val id: Long = 0,

    override val cteType: CteType = CteType.Transform,

    // Packer business name for the creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val cteBusName: BusinessName,

    // ************** KDEs *************
    // (a) Except as specified in paragraphs (b) and (c) of this section,
    // for each new traceability lot of food you produce through transformation
    // you must maintain records containing the following information
    // and linking this information to the new traceability lot:

    // (a)(1) For the food on the Food Traceability List used in transformation (if applicable),
    // the following information:

    // (a)(1)(i) The traceability lot code for the food;
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val packTlc: TraceabilityLotCode,  // from Initial Packer

    // (a)(1)(ii) The product description for the food to which the traceability
    // lot code applies; and
    val packProdDesc: String, // from Initial Packer

    // (a)(1)(iii) For each traceability lot used, the quantity and unit of measure
    // of the food used from that lot.
    val packQuantity: Double,   // from Initial Packer
    val packUnitOfMeasure: UnitOfMeasure,   // from Initial Packer

    // (a)(2) For the food produced through transformation, the following
    // information:

    // (a)(2)(i) The new traceability lot code for the food;
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val transformTlc: TraceabilityLotCode,

    // (a)(2)(ii) The location description for where you transformed
    // the food (i.e., the traceability lot code source),
    // and (if applicable) the traceability lot code source reference;
    val transformLocation: String,

    // (a)(2)(iii) The date transformation was completed;
    val transformDate: OffsetDateTime,

    // (a)(2)(iv) The product description for the food;
    val transformProdDesc: String,

    // (a)(2)(v) The quantity and unit of measure of the
    // food (e.g., 6 cases, 25 reusable plastic containers,
    // 100 tanks, 200 pounds); and
    val transformQuantity: Double,
    val transformUnitOfMeasure: UnitOfMeasure,

    // (a)(2)(vi) The reference document type and reference document
    // number for the transformation event.
    val referenceDocumentType: String,
    val referenceDocumentNum: String,

    // (b) For each traceability lot produced through transformation of a raw
    // agricultural commodity (other than a food obtained from a fishing vessel)
    // on the Food Traceability List that was not initially packed prior to your
    // transformation of the food, you must maintain records containing the
    // information specified in § 1.1330(a) or (c), and, if the raw agricultural
    // commodity is sprouts, the information specified in § 1.1330(b).

    // (c) Paragraphs (a) and (b) of this section do not apply to retail food
    // establishments and restaurants with respect to foods they do not ship
    // (e.g., foods they sell or send directly to consumers).

) : BaseCte<TransformCte>()