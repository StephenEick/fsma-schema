package com.example.fsma.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

//@Entity
data class TraceabilityPlan (
    val descRecordMaintenance: String,
    val descIdentifyFoods: String,
    val descTraceabilityLotCodes: String,
    val pointOfContact: String,
    val farmMap: String,
    @Id @GeneratedValue override val id: Long = 0,
): BaseModel<TraceabilityPlan>()
