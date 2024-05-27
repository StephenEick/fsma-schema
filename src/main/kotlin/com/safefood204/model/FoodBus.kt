package com.safefood204.model

import com.safefood204.util.FoodBusType
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
data class FoodBus(
    @Id @GeneratedValue
    override val id: Long = 0,

    val contactName: String? = null,
    val contactPhone: String? = null,
    val contactEmail: String? = null,

    @ManyToOne @JoinColumn
    val mainAddress: Address,
    val foodBusName: String,

    @Enumerated(EnumType.STRING)
    val foodBusType: FoodBusType,

    // Is this a franchisee?
    @ManyToOne @JoinColumn
    val franchisor: Franchisor? = null,

    val isEnabled: Boolean = true,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null
) : BaseModel<FoodBus>() {
    val isFranchisee: Boolean = franchisor != null
}

data class FoodBusDto(
    val id: Long = 0,
    val contactName: String? = null,
    val contactPhone: String? = null,
    val contactEmail: String? = null,
    val mainAddressId: Long,
    val foodBusName: String,
    val foodBusType: FoodBusType,
    val franchisorId: Long? = null,
    val isEnabled: Boolean = true,
    val dateCreated: OffsetDateTime = OffsetDateTime.now(),
    val dateModified: OffsetDateTime = OffsetDateTime.now(),
    val isDeleted: Boolean = false,
    val dateDeleted: OffsetDateTime? = null,
)

fun FoodBus.toFoodBusinessDto() = FoodBusDto(
    id = id,
    contactName = contactName,
    contactPhone = contactPhone,
    contactEmail = contactEmail,
    mainAddressId = mainAddress.id,
    foodBusName = foodBusName,
    foodBusType = foodBusType,
    franchisorId = franchisor?.id,
    isEnabled = isEnabled,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun FoodBusDto.toBusiness(
    mainAddress: Address,
    franchisor: Franchisor?,
) = FoodBus(
    id = id,
    contactName = contactName,
    contactPhone = contactPhone,
    contactEmail = contactEmail,
    mainAddress = mainAddress,
    foodBusName = foodBusName,
    foodBusType = foodBusType,
    franchisor = franchisor,
    isEnabled = isEnabled,
)
