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

fun Business.toBusinessResponseDto() = BusinessResponseDto(
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

data class BusinessRequestDto(
    val id: Long = 0,
    val contactName: String? = null,
    val contactPhone: String? = null,
    val mainAddressId: Long,
    val name: String,
)

data class BusinessResponseDto(
    val id: Long = 0,
    val contactName: String? = null,
    val contactPhone: String? = null,
    val mainAddressId: Long,
    val name: String,
    val dateCreated: OffsetDateTime,
    val dateModified: OffsetDateTime,
    val isDeleted: Boolean,
    val dateDeleted: OffsetDateTime?,
)

fun BusinessRequestDto.toBusiness(mainAddress: Address) = Business(
    id = id,
    contactName = contactName,
    mainAddress = mainAddress,
    name = name,
    contactPhone = contactPhone,
)