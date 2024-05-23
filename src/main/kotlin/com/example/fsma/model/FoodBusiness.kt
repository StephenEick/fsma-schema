package com.example.fsma.model

import com.example.fsma.util.FoodBusType
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
data class FoodBusiness(
    @Id @GeneratedValue override val id: Long = 0,

    val contactName: String? = null,
    val contactPhone: String? = null,
    val contactEmail: String? = null,

    @ManyToOne @JoinColumn
    val mainAddress: Address,
    val businessName: String,
    val foodBusType: FoodBusType,

    // Is this a franchisee?
    @ManyToOne @JoinColumn
    val franchisor: Franchisor? = null,

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
    val contactEmail: String? = null,
    val mainAddressId: Long,
    val businessName: String,
    val foodBusType: FoodBusType,
    val franchisorId: Long? = null,
    val dateCreated: OffsetDateTime = OffsetDateTime.now(),
    val dateModified: OffsetDateTime = OffsetDateTime.now(),
    val isDeleted: Boolean = false,
    val dateDeleted: OffsetDateTime? = null,
)

fun FoodBusiness.toFoodBusinessDto() = FoodBusinessDto(
    id = id,
    contactName = contactName,
    contactPhone = contactPhone,
    contactEmail = contactEmail,
    mainAddressId = mainAddress.id,
    businessName = businessName,
    foodBusType = foodBusType,
    franchisorId = franchisor?.id,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun FoodBusinessDto.toBusiness(
    mainAddress: Address,
    franchisor: Franchisor?,
) = FoodBusiness(
    id = id,
    contactName = contactName,
    contactPhone = contactPhone,
    contactEmail = contactEmail,
    mainAddress = mainAddress,
    businessName = businessName,
    foodBusType = foodBusType,
    franchisor = franchisor,
)

fun FoodBusiness.isFranchisee() = franchisor != null