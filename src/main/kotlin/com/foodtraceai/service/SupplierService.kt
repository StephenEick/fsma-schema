// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.service

import com.foodtraceai.model.FoodBus
import com.foodtraceai.model.cte.CteReceive
import com.foodtraceai.model.supplier.SupCteShip
import com.foodtraceai.service.cte.CteReceiveService
import com.foodtraceai.util.ReferenceDocumentType
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.OffsetDateTime

@Service
class SupplierService(
    private val cteReceiveService: CteReceiveService,
) {
//    fun receiveSupShipCte(
//
//    ): SupCteShip {
//
//    }

    // RFE or Restaurant has received a shipment
    fun receiveSupShipment(
        supCteShip: SupCteShip,
        foodBus: FoodBus,
        receiveDate: LocalDate,
        referenceDocumentType: ReferenceDocumentType,
        referenceDocumentNum: String,
    ): CteReceive {
        val cteReceive = CteReceive(
            foodBus = foodBus,
            tlc = supCteShip.tlc,
            quantity = supCteShip.quantity,
            unitOfMeasure = supCteShip.unitOfMeasure,
            foodItem = supCteShip.foodItem,
            variety = supCteShip.variety,
            foodDesc = supCteShip.foodDesc,
            prevSourceLocation = supCteShip.shipFromLocation,
            receiveLocation = supCteShip.shipToLocation,
            receiveDate = receiveDate,
            receiveTime = OffsetDateTime.now(),
            tlcSource = supCteShip.tlcSource,
            tlcSourceReference = supCteShip.tlcSourceReference,
            referenceDocumentType = referenceDocumentType,
            referenceDocumentNum = referenceDocumentNum,
        )
        return cteReceiveService.save(cteReceive)
    }
}
