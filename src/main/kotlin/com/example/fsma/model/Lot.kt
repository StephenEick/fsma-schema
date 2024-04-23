package com.example.fsma.model

import com.example.fsma.model.cte.BaseCte
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Lot(
    @Id @GeneratedValue val id: Long = 0,
    val desc: String,
    val criticalTrackingEvents:List<BaseCte<Lot>>
)