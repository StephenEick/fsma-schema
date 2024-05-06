package com.example.fsma.model.cte

import com.example.fsma.model.BaseModel
import com.example.fsma.model.BusinessName
import com.example.fsma.util.CteType

/**
 *** Base superclass of Critical Tracking Events
 **/
abstract class BaseCte<T> : BaseModel<T>() {
    abstract val cteType: CteType
//    abstract val tlc: TraceabilityLotCode

//    // TODO Remove? HarvestCte & CoolCte don't have a food item
//    abstract val commodity: FtlItem
//    abstract val commodityVariety: String

    // Your business name for the creator of this CTE
    abstract val cteBusName: BusinessName
}
