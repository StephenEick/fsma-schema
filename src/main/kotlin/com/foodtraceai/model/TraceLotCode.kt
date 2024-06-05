// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.OffsetDateTime

@Entity
data class TraceLotCode(
    @Id @GeneratedValue
    override val id: Long = 0,
    val tlc: String,
    val desc: String? = null,
    val gtin: String? = null,
    val lot: String? = null,
    val case: String? = null,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null
) : BaseModel<TraceLotCode>()

data class TraceLotCodeDto(
    val id: Long = 0,
    val tlc: String,
    val desc: String?,
    val gtin: String?,
    val lot: String?,
    val case: String?,
    val dateCreated: OffsetDateTime = OffsetDateTime.now(),
    val dateModified: OffsetDateTime = OffsetDateTime.now(),
    val isDeleted: Boolean = false,
    val dateDeleted: OffsetDateTime? = null,
)

// TODO: TraceLotCode and TraceLotCodeDto are identical for now
// but I expect this to change in the future
fun TraceLotCode.toTraceLotCodeDto() = TraceLotCodeDto(
    id = id,
    tlc = tlc,
    desc = desc,
    gtin = gtin,
    lot = lot,
    case = case,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun TraceLotCodeDto.toTraceLotCode() = TraceLotCode(
    id = id,
    tlc = tlc,
    desc = desc,
    gtin = gtin,
    lot = lot,
    case = case,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)
