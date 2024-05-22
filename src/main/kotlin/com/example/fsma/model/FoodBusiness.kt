package com.example.fsma.model

import com.example.fsma.util.FoodBusType
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
data class FoodBusiness(
    @Id @GeneratedValue override val id: Long = 0,

    val contactName: String? = null,
    val contactPhone: String? = null,

    @ManyToOne @JoinColumn
    val mainAddress: Address,
    val businessName: String,
    val foodBusType: FoodBusType,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null
) : BaseModel<FoodBusiness>()

data class FoodBusinessDto(
    val id: Long = 0,
    val contactName: String? = null,
    val contactPhone: String? = null,
    val mainAddressId: Long,
    val businessName: String,
    val foodBusType: FoodBusType,
    val dateCreated: OffsetDateTime= OffsetDateTime.now(),
    val dateModified: OffsetDateTime= OffsetDateTime.now(),
    val isDeleted: Boolean = false,
    val dateDeleted: OffsetDateTime? = null,
)

fun FoodBusiness.toFoodBusinessDto() = FoodBusinessDto(
    id = id,
    contactName = contactName,
    contactPhone = contactPhone,
    mainAddressId = mainAddress.id,
    businessName = businessName,
    foodBusType = foodBusType,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun FoodBusinessDto.toBusiness(mainAddress: Address) = FoodBusiness(
    id = id,
    contactName = contactName,
    contactPhone = contactPhone,
    mainAddress = mainAddress,
    businessName = businessName,
    foodBusType = foodBusType,
)