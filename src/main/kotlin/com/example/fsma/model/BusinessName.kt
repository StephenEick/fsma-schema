package com.example.fsma.model

import jakarta.persistence.*

@Entity
data class BusinessName(
    @Id @GeneratedValue
    val id: Long = 0,
    val name: String,
    val contactName: String? = null,
    val contactPhone : String,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    var businessAddress: Address,
)