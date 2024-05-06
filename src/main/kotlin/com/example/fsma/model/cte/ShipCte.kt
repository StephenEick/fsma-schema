package com.example.fsma.model.cte

import com.example.fsma.model.BusinessName
import com.example.fsma.model.TraceabilityLotCode
import com.example.fsma.util.CteType
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1340

§ 1.1340 What records must I keep and provide when I ship a food on the
Food Traceability List?
 **/

@Entity
data class ShipCte(
    @Id @GeneratedValue
    override val id: Long = 0,

    override val cteType: CteType = CteType.Ship,

    // Shipper business name for the creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val cteBusName: BusinessName,

    // ************** KDEs *************
    // (a) For each traceability lot of a food on the Food Traceability List
    // you ship, you must maintain records containing the following information
    // and linking this information to the traceability lot:

    // (a)(1) The traceability lot code for the food;
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val tlc: TraceabilityLotCode,  // from Initial Packer or Transformer

    // (a)(2) The quantity and unit of measure of the food
    // (e.g., 6 cases, 25 reusable plastic containers, 100 tanks, 200 pounds);
    val quantity: Double,   // from Initial Packer or Transformer
    val unitOfMeasure: UnitOfMeasure,   // from Initial Packer or Transformer

    // (a)(3) The product description for the food;
    val prodDesc: String,

    // (a)(4) The location description for the immediate subsequent recipient
    // (other than a transporter) of the food;
    val shipToLocation: String,

    // (a)(5) The location description for the location from which you shipped
    // the food;
    val shipFromLocation: String,

    // (a)(6) The date you shipped the food;
    val shipDate: OffsetDateTime,

    // (a)(7) The location description for the traceability lot code source,
    // or the traceability lot code source reference; and
    val tlCSource: TraceabilityLotCode,

    // (a)(8) The reference document type and reference document number.
    val referenceDocumentType: String,
    val referenceDocumentNum: String,

    // (b) You must provide (in electronic, paper, or other written form) the
    // information in paragraphs (a)(1) through (7) of this section
    // to the immediate subsequent recipient (other than a transporter)
    // of each traceability lot that you ship.

    // (c) This section does not apply to the shipment of a food that occurs
    // before the food is initially packed (if the food is a raw agricultural
    // commodity not obtained from a fishing vessel).
) : BaseCte<ShipCte>()