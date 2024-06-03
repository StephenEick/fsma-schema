// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.model.supplier

import com.foodtraceai.model.BaseModel
import com.foodtraceai.model.FoodBus
import com.foodtraceai.model.Location
import com.foodtraceai.model.TraceLotCode
import com.foodtraceai.util.CteType
import com.foodtraceai.util.FtlItem
import com.foodtraceai.util.UnitOfMeasure
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime

// This data structure is to receive a supplier shipping event

/**
https://producetraceability.org/wp-content/uploads/2024/02/PTI-FSMA-204-Implementation-Guidance-FINAL-2.12.24.pdf
look at p.26

https://www.ecfr.gov/current/title-21/chapter-I/subchapter-A/part-1/subpart-S/subject-group-ECFRbfe98fb65ccc9f7/section-1.1340
§ 1.1340 What records must I keep and provide when I ship a food on the
Food Traceability List?
 **/

@Entity
data class SupCteShip(
    @Id @GeneratedValue override val id: Long = 0,

    // Supplier sent you a shipment
    val cteType: CteType = CteType.SupShip,

    // Business name for supplier from which you received the food
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val foodBus: FoodBus,

    // ************** KDEs *************
    // (a) For each traceability lot of a food on the Food Traceability List
    // you ship, you must maintain records containing the following information
    // and linking this information to the traceability lot:

    // (a)(1) The traceability lot code for the food;
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val tlc: TraceLotCode,  // from the supplier

    // (a)(2) The quantity and unit of measure of the food
    // (e.g., 6 cases, 25 reusable plastic containers, 100 tanks, 200 pounds);
    val quantity: Double,   // from the supplier
    @Enumerated(EnumType.STRING)
    val unitOfMeasure: UnitOfMeasure,   // from the supplier

    // (a)(3) The product description for the food;
    @Enumerated(EnumType.STRING)
    val foodItem: FtlItem,
    val variety: String,
    val foodDesc: String,

    // (a)(4) The location description for the immediate subsequent recipient
    // (other than a transporter) of the food;
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val shipToLocation: Location,   // Where you are receiving the food

    // (a)(5) The location description for the location from which you shipped
    // the food;
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val shipFromLocation: Location, // Supplier location

    // (a)(6) The date you shipped the food;
    val shipDate: LocalDate,

    // (a)(7) The location description for the traceability lot code source,
    // or the traceability lot code source reference; and
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val tlcSource: Location? = null,
    val tlcSourceReference: String? = null,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null,

    // (b) You must provide (in electronic, paper, or other written form) the
    // information in paragraphs (a)(1) through (7) of this section
    // to the immediate subsequent recipient (other than a transporter)
    // of each traceability lot that you ship.

) : BaseModel<SupCteShip>()

data class SupCteShipDto(
    val id: Long,
    val cteType: CteType,
    val foodBusId: Long,
    val foodItem: FtlItem,
    val variety: String,
    val tlcId: Long,
    val quantity: Double,
    val unitOfMeasure: UnitOfMeasure,
    val foodDesc: String,
    val shipToLocationId: Long,
    val shipFromLocationId: Long,
    val shipDate: LocalDate,
    val tlcSourceId: Long?,
    val tlcSourceReference: String?,
    val dateCreated: OffsetDateTime,
    val dateModified: OffsetDateTime,
    val isDeleted: Boolean,
    val dateDeleted: OffsetDateTime?,
)

fun SupCteShip.toSupCteShipDto() = SupCteShipDto(
    id = id,
    cteType = cteType,
    foodBusId = foodBus.id,
    foodItem = foodItem,
    variety = variety,
    tlcId = tlc.id,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    foodDesc = foodDesc,
    shipToLocationId = shipToLocation.id,
    shipFromLocationId = shipFromLocation.id,
    shipDate = shipDate,
    tlcSourceId = tlcSource?.id,
    tlcSourceReference = tlcSourceReference,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun SupCteShipDto.toSupCteShip(
    foodBus: FoodBus,
    tlc: TraceLotCode,
    shipToLocation: Location,
    shipFromLocation: Location,
    tlcSource: Location,
) = SupCteShip(
    id = id,
    cteType = cteType,
    foodBus = foodBus,
    foodItem = foodItem,
    variety = variety,
    tlc = tlc,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    foodDesc = foodDesc,
    shipToLocation = shipToLocation,
    shipFromLocation = shipFromLocation,
    shipDate = shipDate,
    tlcSource = tlcSource,
    tlcSourceReference = tlcSourceReference,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)