package com.example.fsma.model

import jakarta.persistence.Entity

@Entity
data class TraceabilityPlan (
    val descRecordMaintenance: String,
    val descIdentifyFoods: String,
    val descTraceabilityLotCodes: String,
    val pointOfContact: String,
    val farmMap: String,
): BaseModel<TraceabilityPlan>()
