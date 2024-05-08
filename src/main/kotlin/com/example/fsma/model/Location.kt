package com.example.fsma.model

import jakarta.persistence.*

@Entity
data class Location(
    @Id @GeneratedValue val id: Long = 0,

    @ManyToOne @JoinColumn
    val business: Business,

    val contactName: String? = null,
    val contactPhone: String? = null,

    @ManyToOne @JoinColumn
    val serviceAddress: Address,
)

data class LocationDto(
    val id: Long = 0,
    val businessId: Long,
    val contactName: String? = null,
    val contactPhone: String? = null,
    val serviceAddressId: Long,
)

fun Location.toLocationDto() = LocationDto(
    id = id,
    businessId = business.id,
    contactName = contactName,
    contactPhone = contactPhone,
    serviceAddressId = serviceAddress.id,
)

fun LocationDto.toLocation(
    business: Business,
    address: Address,
) = Location(
    id = id,
    business = business,
    contactName = contactName,
    contactPhone = contactPhone,
    serviceAddress = address,
)