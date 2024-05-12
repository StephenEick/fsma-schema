package com.example.fsma.model.cte

import com.example.fsma.model.BaseModel
import com.example.fsma.model.Business
import com.example.fsma.util.CteType
import com.example.fsma.util.FtlItem
import com.example.fsma.util.ReferenceDocumentType

/**
 *** Base superclass of Critical Tracking Events
 **/
abstract class CteBase<T> : BaseModel<T>() {
    abstract val cteType: CteType

    // Your business name for the creator of this CTE
    abstract val cteBusName: Business

    abstract val commodity: FtlItem
    abstract val commodityVariety: String

    // TODO: need retain multiple referenceDocuments for debugging
    abstract val referenceDocumentType: ReferenceDocumentType
    abstract val referenceDocumentNum: String
}
