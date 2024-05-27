package com.safefood204.model.cte

import com.safefood204.model.BaseModel
import com.safefood204.model.FoodBus
import com.safefood204.util.CteType
import com.safefood204.util.ReferenceDocumentType
import com.safefood204.util.UnitOfMeasure

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
