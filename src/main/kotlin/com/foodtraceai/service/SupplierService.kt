// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.service

import com.foodtraceai.model.Location
import com.foodtraceai.model.cte.CteReceive
import com.foodtraceai.model.supplier.SupShipCte
import com.foodtraceai.service.cte.CteReceiveService
import com.foodtraceai.util.ReferenceDocumentType
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.OffsetDateTime

@Service
class SupplierService(
    private val cteReceiveService: CteReceiveService,
) {
    // RFE or Restaurant has received a shipment
    fun receiveSupShipment(
        supShipCte: SupShipCte,
        location: Location,
        receiveDate: LocalDate,
        referenceDocumentType: ReferenceDocumentType,
        referenceDocumentNum: String,
    ): CteReceive {
        val cteReceive = CteReceive(
            location = location,
            tlc = supShipCte.tlc,
            quantity = supShipCte.quantity,
            unitOfMeasure = supShipCte.unitOfMeasure,
            foodItem = supShipCte.foodItem,
            variety = supShipCte.variety,
            foodDesc = supShipCte.foodDesc,
            prevSourceLocation = supShipCte.shipFromLocation,
            receiveLocation = supShipCte.shipToLocation,
            receiveDate = receiveDate,
            receiveTime = OffsetDateTime.now(),
            tlcSource = supShipCte.tlcSource,
            tlcSourceReference = supShipCte.tlcSourceReference,
            referenceDocumentType = referenceDocumentType,
            referenceDocumentNum = referenceDocumentNum,
        )

        return cteReceiveService.save(cteReceive)
    }
}
