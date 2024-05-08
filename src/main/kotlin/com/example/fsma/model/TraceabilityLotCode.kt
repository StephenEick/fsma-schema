package com.example.fsma.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

//@Entity
data class TraceabilityLotCode(
    val tlc: String,
    val desc: String,
    @Id @GeneratedValue val id: Long = 0,
)
