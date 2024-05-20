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
data class IPackSproutsCte(
    @Id @GeneratedValue override val id: Long = 0,

    override val cteType: CteType = CteType.InitPackSprouts,

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

    // TODO - InitPackCte Sprouts.  Ignore for now
    // https://producetraceability.org/wp-content/uploads/2024/02/PTI-FSMA-204-Implementation-Guidance-FINAL-2.12.24.pdf
    // look at p.23

    // (b) For each traceability lot of sprouts (except soil- or substrate-grown
    // sprouts harvested without their roots) you initially pack, you must also
    // maintain records containing the following information and linking this
    // information to the traceability lot:

    // (b)(1) The location description for the grower of seeds for sprouting and the
    // date of seed harvesting, if either is available;

    // (b)(2) The location description for the seed conditioner or processor,
    // the associated seed lot code, and the date of conditioning or processing;

    // (b)(3) The location description for the seed packinghouse (including any repackers),
    // the date of packing (and of repacking, if applicable), and any associated
    // seed lot code assigned by the seed packinghouse;

    // (b)(4) The location description for the seed supplier, any seed lot code
    // assigned by the seed supplier (including the master lot and sub-lot codes),
    // and any new seed lot code assigned by the sprouter;

    // (b)(5) A description of the seeds, including the seed type or taxonomic name,
    // growing specifications, type of packaging, and (if applicable) antimicrobial
    // treatment;

    // (b)(6) The date of receipt of the seeds by the sprouter; and

    // (b)(7) The reference document type and reference document number.
) : CteBase<IPackSproutsCte>()