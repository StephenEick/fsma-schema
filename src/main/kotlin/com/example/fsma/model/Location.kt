package com.example.fsma.model

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
data class Location(
    @Id @GeneratedValue override val id: Long = 0,

    @ManyToOne @JoinColumn
    val foodBus: FoodBus,

    val contactName: String? = null,
    val contactPhone: String? = null,

    @ManyToOne @JoinColumn
    val serviceAddress: Address,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null
) : BaseModel<Location>()

data class LocationDto(
    val id: Long = 0,
    val foodBusId: Long,
    val contactName: String? = null,
    val contactPhone: String? = null,
    val serviceAddressId: Long,
    val dateCreated: OffsetDateTime= OffsetDateTime.now(),
    val dateModified: OffsetDateTime= OffsetDateTime.now(),
    val isDeleted: Boolean = false,
    val dateDeleted: OffsetDateTime?=null,
)

fun Location.toLocationDto() = LocationDto(
    id = id,
    foodBusId = foodBus.id,
    contactName = contactName,
    contactPhone = contactPhone,
    serviceAddressId = serviceAddress.id,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun LocationDto.toLocation(
    foodBus: FoodBus,
    serviceAddress: Address,
) = Location(
    id = id,
    foodBus = foodBus,
    contactName = contactName,
    contactPhone = contactPhone,
    serviceAddress = serviceAddress,
)