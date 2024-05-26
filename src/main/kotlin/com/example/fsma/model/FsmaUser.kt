package com.example.fsma.model

import com.example.fsma.util.Role
import com.example.fsma.util.RoleToJsonConverter
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.OffsetDateTime

@Entity
@Table(
    indexes = [
        Index(columnList = "email"), Index(columnList = "food_business_id")
    ]
)
data class FsmaUser(
    @Id @GeneratedValue
    override val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "food_business_id")
    val foodBus: FoodBus,

    // email is the "username"
    @Column(unique = true)
    @Email
    internal val email: String,
    internal val password: String,

    internal val isAccountNonExpired: Boolean,
    internal val isAccountNonLocked: Boolean,
    internal val isCredentialsNonExpired: Boolean,
    internal var isEnabled: Boolean,

    @Convert(converter = RoleToJsonConverter::class)
    @Column(columnDefinition = "TEXT")
    val roles: List<Role>,

    // ************* User stuff goes here **************
    val firstname: String,
    val lastname: String,
    val notes: String?,
    val phone: String?,

    @Column(updatable = false)
    override var dateCreated: OffsetDateTime = OffsetDateTime.now(),
    override var dateModified: OffsetDateTime = OffsetDateTime.now(),
    override var isDeleted: Boolean = false,
    override var dateDeleted: OffsetDateTime? = null,
) : BaseModel<FsmaUser>(), UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { SimpleGrantedAuthority(it.name) }.toMutableList()

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = isAccountNonExpired

    override fun isAccountNonLocked(): Boolean = isAccountNonLocked

    override fun isCredentialsNonExpired(): Boolean = isCredentialsNonExpired

    override fun isEnabled() = isEnabled && foodBus.isEnabled &&
            (!foodBus.isFranchisee || foodBus.franchisor!!.isEnabled)

    fun isRootAdmin() = roles.contains(Role.RootAdmin)

    fun isFranchisorAdmin() = roles.contains(Role.FranchisorAdmin)

    fun isFoodBusinessAdmin() = roles.contains(Role.FoodBusinessAdmin)

    fun isFoodBusinessUser() = isFoodBusinessAdmin() || roles.contains(Role.FoodBusinessUser)

    fun isMobile() = isFoodBusinessUser() || roles.contains(Role.Mobile)
}

data class FsmaUserDto(
    val id: Long = 0,
    val foodBusinessId: Long,
    @field:Email(message = "A valid email is required")
    val email: String,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String = "",
    val isAccountNonExpired: Boolean = true,
    val isAccountNonLocked: Boolean = true,
    val isCredentialsNonExpired: Boolean = true,
    var isEnabled: Boolean = true,
    val roles: List<Role>,
    // ************* User stuff goes here **************
    val firstname: String,
    val lastname: String,
    val notes: String? = null,
    val phone: String? = null,
    val dateCreated: OffsetDateTime = OffsetDateTime.now(),
    val dateModified: OffsetDateTime = OffsetDateTime.now(),
    val isDeleted: Boolean = false,
    val dateDeleted: OffsetDateTime? = null,
)

fun FsmaUser.toFsmaUserDto() = FsmaUserDto(
    id = id,
    foodBusinessId = foodBus.id,
    email = email,
    password = password,
    isAccountNonExpired = isAccountNonExpired,
    isAccountNonLocked = isAccountNonLocked,
    isCredentialsNonExpired = isCredentialsNonExpired,
    isEnabled = isEnabled,
    roles = roles,
    firstname = firstname,
    lastname = lastname,
    notes = notes,
    phone = phone,
    dateCreated = dateCreated,
    dateModified = dateModified,
    isDeleted = isDeleted,
    dateDeleted = dateDeleted,
)

fun FsmaUserDto.toFsmaUser(foodBus: FoodBus) = FsmaUser(
    id = id,
    foodBus = foodBus,
    email = email,
    password = password,
    isAccountNonExpired = isAccountNonExpired,
    isAccountNonLocked = isAccountNonLocked,
    isCredentialsNonExpired = isCredentialsNonExpired,
    isEnabled = isEnabled,
    roles = roles,
    firstname = firstname,
    lastname = lastname,
    notes = notes,
    phone = phone,
)