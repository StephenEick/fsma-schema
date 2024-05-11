package com.example.fsma.model

import com.example.fsma.model.cte.CteBase
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

//@Entity
data class Lot(
    @Id @GeneratedValue val id: Long = 0,
    val desc: String,
    val criticalTrackingEvents:List<CteBase<Lot>>
)