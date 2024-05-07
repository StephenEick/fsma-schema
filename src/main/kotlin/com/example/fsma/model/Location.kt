package com.example.fsma.model

import jakarta.persistence.Entity

@Entity
data class Location (
    val businessName: BusinessName,
    val address: Address,
)