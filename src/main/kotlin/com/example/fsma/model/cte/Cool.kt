package com.example.fsma.model.cte

import com.example.fsma.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Cool(
    @Id @GeneratedValue
    override val id: Long = 0,
) : BaseCte<Cool>()