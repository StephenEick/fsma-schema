package com.safefood204.model.cte

import com.safefood204.model.FoodBus
import com.safefood204.util.CteType
import com.safefood204.util.FtlItem
import com.safefood204.util.ReferenceDocumentType
import com.safefood204.util.UnitOfMeasure
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

//@Entity
data class FirstLandReceiveCte(
    @Id @GeneratedValue override val id: Long = 0,

    override val cteType: CteType = CteType.Receive,
    override val cteBusName: FoodBus,

    @Enumerated(EnumType.STRING)
    val foodItem: FtlItem,
    val variety: String,
    val foodDesc: String,

    override val quantity: Double,
    override val unitOfMeasure: UnitOfMeasure,

    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,
) : CteBase<FirstLandReceiveCte>()
