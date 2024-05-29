package com.foodtraceai.model.cte

import com.foodtraceai.model.BaseModel
import com.foodtraceai.model.FoodBus
import com.foodtraceai.util.CteType
import com.foodtraceai.util.ReferenceDocumentType
import com.foodtraceai.util.UnitOfMeasure

/**
 *** Base superclass of Critical Tracking Events
 **/
abstract class CteBase<T> : BaseModel<T>() {
    abstract val cteType: CteType

    // Business name for the creator of this CTE
    abstract val cteBusName: FoodBus

    // TODO: remove unneeded lines
//    abstract val foodItem: FtlItem  // or commodity
//    abstract val variety: String
//    abstract val foodDesc: String

    // quantity & unitOfMeasure is the amount after CTE is finished
    abstract val quantity: Double   // from Initial Packer or Transformer
    abstract val unitOfMeasure: UnitOfMeasure   // from Initial Packer or Transformer

    // TODO: need retain multiple referenceDocuments for debugging
    abstract val referenceDocumentType: ReferenceDocumentType
    abstract val referenceDocumentNum: String
}
