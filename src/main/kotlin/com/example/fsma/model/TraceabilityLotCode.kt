package com.example.fsma.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

//@Entity
data class TraceabilityLotCode(
    @Id @GeneratedValue val id: Long = 0,
    val tlc: String,
    val desc: String,
)
