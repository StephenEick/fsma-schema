package com.example.fsma.model.cte

import com.example.fsma.model.BusinessName
import com.example.fsma.model.TraceabilityLotCode
import com.example.fsma.util.CteType
import com.example.fsma.util.UnitOfMeasure
import com.fasterxml.jackson.datatype.jsr310.deser.key.OffsetDateTimeKeyDeserializer
import jakarta.persistence.*
/**
https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1345

§ 1.1345 What records must I keep when I receive a food on the
Food Traceability List?
 **/
@Entity

data class ReceiveCte(
    @Id @GeneratedValue
    override val id: Long = 0,

    override val cteType: CteType = CteType.Receive,

    // Business name for the creator of this CTE
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    override val cteBusName: BusinessName,

    // ************** KDEs *************
    // (a) Except as specified in paragraphs (b) and (c) of this section,
    // for each traceability lot of a food on the Food Traceability List
    // you receive, you must maintain records containing the following
    // information and linking this information to the traceability lot:

    // (a)(1) The traceability lot code for the food;
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val tlc: TraceabilityLotCode,

    // (a)(2) The quantity and unit of measure of the food
    // (e.g., 6 cases, 25 reusable plastic containers, 100 tanks, 200 pounds);
    val quantity: Double,
    val unitOfMeasure: UnitOfMeasure,

    // (a)(3) The product description for the food;
    val prodDesc: String,

    // (a)(4) The location description for the immediate previous source
    // (other than a transporter) for the food;
    val previousSourceLocation: String,

    // (a)(5) The location description for where the food was received;
    val receiveLocation: String,

    // (a)(6) The date you received the food;
    val receiveDate: OffsetDateTimeKeyDeserializer,

    // (a)(7) The location description for the traceability lot code source,
    // or the traceability lot code source reference; and
    val tlcSource: BusinessName,

    // (a)(8) The reference document type and reference document number.
    val referenceDocumentType: String,
    val referenceDocumentNum: String,

    //(b) For each traceability lot of a food on the Food Traceability List
    // you receive from a person to whom this subpart does not apply,
    // you must maintain records containing the following information and
    // linking this information to the traceability lot:

    //(1) The traceability lot code for the food, which you must assign if
    // one has not already been assigned (except that this paragraph does not
    // apply if you are a retail food establishment or restaurant);
    //
    //(2) The quantity and unit of measure of the food (e.g., 6 cases,
    // 25 reusable plastic containers, 100 tanks, 200 pounds);
    //
    //(3) The product description for the food;
    //
    //(4) The location description for the immediate previous source
    // (other than a transporter) for the food;
    //
    //(5) The location description for where the food was received
    // (i.e., the traceability lot code source), and (if applicable)
    // the traceability lot code source reference;
    //
    //(6) The date you received the food; and
    //
    //(7) The reference document type and reference document number.
    //
    //(c) This section does not apply to receipt of a food that occurs
    // before the food is initially packed (if the food is a raw agricultural
    // commodity not obtained from a fishing vessel) or to the receipt
    // of a food by the first land-based receiver (if the food is obtained
    // from a fishing vessel).
) : BaseCte<ReceiveCte>()