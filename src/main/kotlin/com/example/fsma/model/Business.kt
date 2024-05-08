package com.example.fsma.model

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
data class Business(
    @Id @GeneratedValue override val id: Long = 0,
    val contactName: String? = null,
    val contactPhone: String? = null,
    @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn
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
    val mainAddressId: Long,
    val name: String,
    val contactPhone: String? = null,
)

fun Business.toBusinessNameDto() = BusinessDto(
    id = id,
    contactName = contactName,
    mainAddressId = mainAddress.id,
    name = name,
    contactPhone = contactPhone,
)

fun BusinessDto.toBusinessName(address: Address) = Business(
    id = id,
    contactName = contactName,
    mainAddress = address,
    name = name,
    contactPhone = contactPhone,
)