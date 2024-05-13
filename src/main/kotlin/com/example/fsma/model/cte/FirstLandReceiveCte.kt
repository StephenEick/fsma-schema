package com.example.fsma.model.cte

import com.example.fsma.model.Business
import com.example.fsma.util.CteType
import com.example.fsma.util.FtlItem
import com.example.fsma.util.ReferenceDocumentType
import com.example.fsma.util.UnitOfMeasure
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

//@Entity
data class FirstLandReceiveCte(
    @Id @GeneratedValue override val id: Long = 0,

    override val cteType: CteType = CteType.Receive,
    override val cteBusName: Business,

    @Enumerated(EnumType.STRING)
    val foodItem: FtlItem,
    val variety: String,
    val foodDesc: String,

    override val quantity: Double,
    override val unitOfMeasure: UnitOfMeasure,

    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,
) : CteBase<FirstLandReceiveCte>()
