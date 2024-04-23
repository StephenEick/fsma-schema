package com.example.fsma.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class FsmaUser(
    val login: String,
    val firstname: String,
    val lastname: String,
    val description: String? = null,
    @Id @GeneratedValue val id: Long = 0
)
