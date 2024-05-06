package com.example.fsma.model.cte

import com.example.fsma.model.Address
import com.example.fsma.model.BusinessName
import com.example.fsma.model.TraceabilityLotCode
import com.example.fsma.util.CteType
import com.example.fsma.util.FtlItem
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1330

§ 1.1330 What records must I keep when I am performing the initial packing of a raw
agricultural commodity (other than a food obtained from a fishing vessel) on the
Food Traceability List?
 **/

@Entity
data class InitialPackCte(
    @Id @GeneratedValue

    override val id: Long = 0,
    override val cteType: CteType = CteType.InitialPacking,

    // Packer business name for the creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val businessName: BusinessName,

    // ************** KDEs *************
    // (a) Except as specified in paragraph (c) of this section, for each traceability
    // lot of a raw agricultural commodity (other than a food obtained from a fishing vessel)
    // on the Food Traceability List you initially pack, you must maintain records
    // containing the following information and linking this information to the
    // traceability lot:

    // (a)(1) The commodity and, if applicable, variety of the food;
    override val commodity: FtlItem,
    override val commodityVariety: String,

    // (a)(2) The date you received the food;
    val receiveDate: OffsetDateTime,

    // (a)(3) The quantity and unit of measure of the food received (e.g., 75 bins, 200 pounds);
    val receiveQuantity: Double,
    val receiveUnitOfMeasure: UnitOfMeasure,

    // (a)(4) The location description for the farm where the food was harvested;
    val harvestLocation: String,

    // (a)(5) For produce, the name of the field or other growing area from which the
    // food was harvested (which must correspond to the name used by the grower),
    // or other information identifying the harvest location at least as precisely
    // as the field or other growing area name;
    val growingAreaName: String,
    val growingAreaDesc: String,

    // (a)(6) For aquacultured food, the name of the container
    // (e.g., pond, pool, tank, cage) from which the food was harvested
    // (which must correspond to the container name
    // used by the aquaculture farmer) or other information identifying the harvest
    // location at least as precisely as the container name;
    val containerName: String,
    val containerDesc: String,

    // (a)(7) The business name and phone number for the harvester of the food
    val harvestBusinessName: BusinessName,

    // (a)(8) The date of harvesting;
    val harvestDate: OffsetDateTime,

    // (a)(9) The location description for where the food was cooled (if applicable);
    val coolingLocation: String? = null,

    // (a)(10) The date of cooling (if applicable);
    val coolingDate: OffsetDateTime? = null,

    // (a)(11) The traceability lot code you assigned;
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val tlc: TraceabilityLotCode,

    // (a)(12) The product description of the packed food;
    val prodDescPackedFood: String,

    // (a)(13) The quantity and unit of measure of the packed food (e.g., 6 cases, 25 reusable plastic containers, 100 tanks, 200 pounds);
    val packQuantity: Double,
    val packUnitOfMeasure: UnitOfMeasure,

    // (a)(14) The location description for where you initially packed the food
    // (i.e., the traceability lot code source), and (if applicable) the traceability
    // lot code source reference;
    // TODO: Type tlcSource, tlcReference
    val tlcSource: String,
    val tlcSourceReference: String,

    // (a)(15) The date of initial packing; and
    val packDate: OffsetDateTime,

    // (a)(16) The reference document type and reference document number.
    val referenceDocumentType: String,
    val referenceDocumentNum: String,

    // TODO - Sprouts.  Ignore for now
    // (b) For each traceability lot of sprouts (except soil- or substrate-grown
    // sprouts harvested without their roots) you initially pack, you must also
    // maintain records containing the following information and linking this
    // information to the traceability lot:

    // TODO - Packing.  Ignore for now
    // (c) For each traceability lot of a raw agricultural commodity (other than a
    // food obtained from a fishing vessel) on the Food Traceability List you initially
    // pack that you receive from a person to whom this subpart does not apply,
    // you must maintain records containing the following information and linking
    // this information to the traceability lot:
) : BaseCte<InitialPackCte>()