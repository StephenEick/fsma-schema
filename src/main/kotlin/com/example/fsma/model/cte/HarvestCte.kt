package com.example.fsma.model.cte

import com.example.fsma.model.BusinessName
import com.example.fsma.model.TraceabilityLotCode
import com.example.fsma.util.CteType
import com.example.fsma.util.FtlItem
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1325#p-1.1325(a)

§ 1.1325 What records must I keep and provide when I harvest or cool
a raw agricultural commodity on the Food Traceability List?
 **/

// (a) Harvesting.
@Entity
data class HarvestCte(
    @Id @GeneratedValue
    override val id: Long = 0,

    override val cteType: CteType = CteType.Harvest,

    // ************** KDEs *************
    // (1) For each raw agricultural commodity (not obtained from a fishing vessel)
    // on the Food Traceability List that you harvest, you must maintain records
    // containing the following information:

    // (1)(i) The location description for the immediate subsequent recipient
    // (other than a transporter) of the food;
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    var subsequentRecipient: BusinessName,

    // (1)(ii) The commodity and, if applicable, variety of the food;
    val commodity: FtlItem,
    val commodityVariety: String,

    // (1)(iii) The quantity and unit of measure of the food (e.g., 75 bins, 200 pounds);
    val harvestQuantity: Double,
    val harvestUnitOfMeasure: UnitOfMeasure,

    // (1)(iv) The location description for the farm where the food was harvested;
    val harvestLocation: String,

    // (1)(v) For produce, the name of the field or other growing area from which the
    // food was harvested (which must correspond to the name used by the grower),
    // or other information identifying the harvest location at least as precisely
    // as the field or other growing area name;
    val growingAreaName: String,
    val growingAreaDesc: String,

    // (1)(vi) For aquacultured food, the name of the container
    // (e.g., pond, pool, tank, cage) from which the food was harvested
    // (which must correspond to the container name
    // used by the aquaculture farmer) or other information identifying the harvest
    // location at least as precisely as the container name;
    val containerName: String,
    val containerDesc: String,

    // (1)(vii) The date of harvesting;
    val harvestDate: OffsetDateTime,

    // (1)(viii) The reference document type and reference document number.
    val harvestReferenceDocumentType: String,
    val harvestReferenceDocumentNum: String,

    // (2) For each raw agricultural commodity (not obtained from a fishing vessel)
    // on the Food Traceability List that you harvest, you must provide (in electronic,
    // paper, or other written form) your business name, phone number, and the
    // information in paragraphs (a)(1)(i) through (vii) of this section to the
    // initial packer of the raw agricultural commodity you harvest, either directly
    // or through the supply chain.

    // Harvest business name, e.g. creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val cteBusName: BusinessName,
) : BaseCte<HarvestCte>()