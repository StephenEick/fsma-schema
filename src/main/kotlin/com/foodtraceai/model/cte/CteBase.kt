// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.model.cte

import com.foodtraceai.model.BaseModel
import com.foodtraceai.model.FoodBus
import com.foodtraceai.model.Location
import com.foodtraceai.util.CteType
import com.foodtraceai.util.FtlItem
import com.foodtraceai.util.ReferenceDocumentType
import com.foodtraceai.util.UnitOfMeasure

/**
 *** Base superclass of Critical Tracking Events
 **/
abstract class CteBase<T> : BaseModel<T>() {
    abstract val cteType: CteType

    //    // TODO: remove and pick this up from location?
//    // Business name for the creator of this CTE
//    abstract val foodBus: FoodBus
    val foodBus: FoodBus
        get() = location.foodBus

    // Location where this CTE is created
    abstract val location: Location

    // Common to all CTEs.  For raw agricultural commodities use commodity name
    abstract val foodItem: FtlItem  // or commodity
    abstract val variety: String
    abstract val foodDesc: String
    //TODO: Add product name, brand, commodity, and variety,
    // packaging size, packing style,
    // for fish: may include species and/or market name
    // TODO: Do it as a json text

    // quantity & unitOfMeasure is the amount after CTE is finished
    abstract val quantity: Double   // from Initial Packer or Transformer
    abstract val unitOfMeasure: UnitOfMeasure   // from Initial Packer or Transformer

    // TODO: need retain a list referenceDocuments for debugging
    abstract val referenceDocumentType: ReferenceDocumentType
    abstract val referenceDocumentNum: String
}
