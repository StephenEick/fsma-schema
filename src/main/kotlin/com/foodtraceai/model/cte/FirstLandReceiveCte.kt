// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.model.cte

import com.foodtraceai.model.FoodBus
import com.foodtraceai.util.CteType
import com.foodtraceai.util.FtlItem
import com.foodtraceai.util.ReferenceDocumentType
import com.foodtraceai.util.UnitOfMeasure
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
    override val foodItem: FtlItem,
    override val variety: String,
    override val foodDesc: String,

    override val quantity: Double,
    override val unitOfMeasure: UnitOfMeasure,

    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,
) : CteBase<FirstLandReceiveCte>()
