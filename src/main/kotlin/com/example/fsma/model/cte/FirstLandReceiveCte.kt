package com.example.fsma.model.cte

import com.example.fsma.model.BusinessName
import com.example.fsma.util.CteType
import com.example.fsma.util.ReferenceDocumentType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class FirstLandReceiveCte(
    @Id @GeneratedValue override val id: Long = 0,

    override val cteType: CteType = CteType.Receive,
    override val cteBusName: BusinessName,

    override val referenceDocumentType: ReferenceDocumentType,
    override val referenceDocumentNum: String,
) : BaseCte<FirstLandReceiveCte>()
