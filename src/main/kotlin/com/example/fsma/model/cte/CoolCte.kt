package com.example.fsma.model.cte

import com.example.fsma.model.BusinessName
import com.example.fsma.model.TraceabilityLotCode
import com.example.fsma.util.CteType
import com.example.fsma.util.FtlItem
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1325#p-1.1325(b)

§ 1.1325 What records must I keep and provide when I harvest or cool
a raw agricultural commodity on the Food Traceability List?
 **/

// (b) Cooling before initial packing.
@Entity
data class CoolCte(
    @Id @GeneratedValue
    override val id: Long = 0,

    override val cteType: CteType = CteType.Cool,

    // ************** KDEs *************
    // (1) For each raw agricultural commodity
    // (not obtained from a fishing vessel) on the Food Traceability List
    // that you cool before it is initially packed, you must maintain records
    // containing the following information:

    // (1)(i) The location description for the immediate subsequent
    // recipient (other than a transporter) of the food;
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    var coolSubsequentRecipient: BusinessName,

    // (1)(ii) The commodity and, if applicable, variety of the food;
    val commodity: FtlItem,
    val commodityVariety: String,

    // (1)(iii) The quantity and unit of measure of the food (e.g., 75 bins, 200 pounds);
    val coolingQuantity: Double,
    val unitOfMeasure: UnitOfMeasure,

    // (1)(iv) The location description for where you cooled the food;
    val coolingLocation: String,
    val coolingUnitOfMeasure: UnitOfMeasure,

    // (1)(v) The date of cooling;
    val coolingDate: OffsetDateTime,

    // (1)(vi) The location description for the farm where the food was harvested;
    val harvestLocation: String,

    // (1)(vii) The reference document type and reference document number.
    val coolReferenceDocumentType: String,
    val coolReferenceDocumentNum: String,

    // (2) For each raw agricultural commodity (not obtained from a fishing vessel)
    // on the Food Traceability List that you harvest, you must provide (in electronic,
    // paper, or other written form) your business name, phone number, and the
    // information in paragraphs (a)(1)(i) through (vii) of this section to the
    // initial packer of the raw agricultural commodity you harvest, either directly
    // or through the supply chain.

    // Cooler business name
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val cteBusName: BusinessName,
) : BaseCte<CoolCte>()