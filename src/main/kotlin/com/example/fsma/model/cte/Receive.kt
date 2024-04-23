package com.example.fsma.model.cte

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Receive(
    @Id @GeneratedValue
    override val id: Long = 0,
) : BaseCte<Receive>()