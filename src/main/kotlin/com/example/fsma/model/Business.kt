package com.example.fsma.model

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
data class Business(
    @Id @GeneratedValue override val id: Long = 0,

    val contactName: String? = null,
    val contactPhone: String? = null,

    @ManyToOne @JoinColumn
    val mainAddress: Address,
    val name: String,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null
) : BaseModel<Business>()

data class BusinessDto(
    val id: Long = 0,
    val contactName: String? = null,
    val contactPhone: String? = null,
    val mainAddressId: Long,
    val name: String,
    val dateCreated: OffsetDateTime= OffsetDateTime.now(),
    val dateModified: OffsetDateTime= OffsetDateTime.now(),
    val isDeleted: Boolean = false,
    val dateDeleted: OffsetDateTime? = null,
)

fun Business.toBusinessDto() = BusinessDto(
    id = id,
    contactName = contactName,
    contactPhone = contactPhone,
    mainAddressId = mainAddress.id,
    name = name,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun BusinessDto.toBusiness(mainAddress: Address) = Business(
    id = id,
    contactName = contactName,
    contactPhone = contactPhone,
    mainAddress = mainAddress,
    name = name,
)