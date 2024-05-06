package com.example.fsma.model.cte

import com.example.fsma.util.CteType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class FirstLandReceiver(
    @Id @GeneratedValue
    override val id: Long = 0,

    override val cteType: CteType = CteType.Cooling,
    override val businessName: String,
    override val businessPhone: String,

) : BaseCte<FirstLandReceiver>()