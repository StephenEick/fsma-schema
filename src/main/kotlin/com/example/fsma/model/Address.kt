package com.example.fsma.model

import com.example.fsma.util.Country
import com.example.fsma.util.UsaCanadaState
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
//@EntityListeners(BaseModelEntityListener::class)
data class Address(
    @Id @GeneratedValue override val id: Long = 0,

//    @ManyToOne @JoinColumn // (name = "reseller_id")
//    val resellerId: Long = 0,

//    @ManyToOne @JoinColumn // (name = "client_id")
//    val clientId: Long,

    val street: String,
    val street2: String? = null,
    val city: String,
    @Enumerated(EnumType.STRING)
    val state: UsaCanadaState,
    val postalCode: String,
    @Enumerated(EnumType.STRING)
    val country: Country, // Country = Country.USA,
    val lat: Double,
    val lng: Double,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null

) : BaseModel<Address>()

data class AddressDto(
    val id: Long = 0,
//    val resellerId: Long? = null,
    val street: String,
    val street2: String? = null,
    val city: String,
    val state: UsaCanadaState,
    val postalCode: String,
    val country: Country,
    val lat: Double,
    val lng: Double,
    val dateCreated: OffsetDateTime= OffsetDateTime.now(),
    val dateModified: OffsetDateTime= OffsetDateTime.now(),
    val isDeleted: Boolean = false,
    val dateDeleted: OffsetDateTime? = null,
)

fun Address.toAddressDto() = AddressDto(
    id = id,
//    resellerId = resellerId,
    street = street,
    street2 = street2,
    city = city,
    state = state,
    postalCode = postalCode,
    country = country,
    lat = lat,
    lng = lng,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun AddressDto.toAddress() = Address(
    id = id,
//    resellerId = resellerId,
    street = street,
    street2 = street2,
    city = city,
    state = state,
    postalCode = postalCode,
    country = country,
    lat = lat,
    lng = lng,
)
