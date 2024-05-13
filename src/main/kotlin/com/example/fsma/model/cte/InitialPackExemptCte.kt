package com.example.fsma.model.cte

import com.example.fsma.model.Business
import com.example.fsma.util.CteType
import com.example.fsma.util.FtlItem
import com.example.fsma.util.ReferenceDocumentType
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.*

/**
https://producetraceability.org/wp-content/uploads/2024/02/PTI-FSMA-204-Implementation-Guidance-FINAL-2.12.24.pdf
Look at p.24

https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1330
§ 1.1330 What records must I keep when I am performing the initial packing of a raw
agricultural commodity (other than a food obtained from a fishing vessel) on the
Food Traceability List?
 **/

//@Entity
data class InitialPackExemptCte(
    @Id @GeneratedValue override val id: Long = 0,

    override val cteType: CteType = CteType.InitialPack,

    // Packer business name for the creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val cteBusName: Business,

    @Enumerated(EnumType.STRING)
    val foodItem: FtlItem,
    val variety: String,
    val foodDesc: String,

    override val quantity: Double,
    @Enumerated(EnumType.STRING)
    override val unitOfMeasure: UnitOfMeasure,

    // ************** KDEs *************

    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,

    // TODO - InitPackCte Exempt Entities.  Ignore for now
    // https://producetraceability.org/wp-content/uploads/2024/02/PTI-FSMA-204-Implementation-Guidance-FINAL-2.12.24.pdf
    // look at p.23

    // (c) For each traceability lot of a raw agricultural commodity (other than a
    // food obtained from a fishing vessel) on the Food Traceability List you initially
    // pack that you receive from a person to whom this subpart does not apply,
    // you must maintain records containing the following information and linking
    // this information to the traceability lot:

    // (c)(1) The commodity and, if applicable, variety of the food received;

    // (c)(2) The date you received the food;

    // (c)(3) The quantity and unit of measure of the food received
    // (e.g., 75 bins, 200 pounds);

    // (c)(4) The location description for the person from whom you received the food;

    // (c)(5) The traceability lot code you assigned;

    // (c)(6) The product description of the packed food;

    // (c)(7) The quantity and unit of measure of the packed food
    // (e.g., 6 cases, 25 reusable plastic containers, 100 tanks, 200 pounds);

    // (c)(8) The location description for where you initially packed the food
    // (i.e., the traceability lot code source), and (if applicable) the
    // traceability lot code source reference;

    // (c)(9) The date of initial packing; and

    // (c)(10) The reference document type and reference document number.

) : CteBase<InitialPackExemptCte>()