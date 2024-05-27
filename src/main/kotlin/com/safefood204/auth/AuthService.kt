package com.safefood204.auth

import com.safefood204.model.FsmaUserDto
import com.safefood204.model.toFsmaUser
import com.safefood204.repository.FoodBusRepository
import com.safefood204.repository.FsmaUserRepository
import com.safefood204.service.FsmaUserService
import com.safefood204.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class AuthService {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var foodBusRepository: FoodBusRepository

    @Autowired
    private lateinit var fsmaUserService: FsmaUserService

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var fsmaUserRepository: FsmaUserRepository

//    @Autowired
//    private lateinit var kNotificationApiWrapper: KNotificationApiWrapper

    // token timeout for expiration
    private val expiresIn: Duration = 2.hours

    // Register a new user
    fun createNewFsmaUser(newFsmaUserDto: FsmaUserDto): AuthResponse {
        // Check to make sure user does not already exist
        if (fsmaUserService.findByEmailIgnoreCase(newFsmaUserDto.email) != null)
            throw EntityExistsException("User already exists: ${newFsmaUserDto.email}")

        val foodBusiness = foodBusRepository.findByIdOrNull(newFsmaUserDto.foodBusinessId)
            ?: throw EntityNotFoundException("FoodBusiness not found: ${newFsmaUserDto.foodBusinessId}")

        val newFsmaUser = newFsmaUserDto.toFsmaUser(foodBusiness)
            .copy(password = passwordEncoder.encode(newFsmaUserDto.password), email = newFsmaUserDto.email.lowercase())
        val newFsmaUserId = fsmaUserService.save(newFsmaUser).id

        // Embed the resellerId, clientId, userId in the token
        val extraClaims = jwtService.createExtraClaims(foodBusiness.id, newFsmaUserId)

        return AuthResponse(
            accessToken = jwtService.generateToken(newFsmaUser, extraClaims),
            tokenType = "Bearer",
            expiresIn = expiresIn,
            refreshToken = jwtService.generateToken(newFsmaUser),
            foodBusinessId = foodBusiness.id,
            fsmaUserId = newFsmaUserId,
        )
    }

    // Authenticate an existing user
    fun authenticate(email: String, password: String): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )

        return authResponse(email)
    }

    fun authResponse(email: String): AuthResponse {
        // Look up user details
        val fsmaUser = fsmaUserService.findByEmailIgnoreCase(email)
            ?: throw AuthorizationException("FsmaUser $email not authorized")

        // Embed the resellerId, clientId, userId in the token
        val extraClaims = jwtService.createExtraClaims(fsmaUser.foodBus.id, fsmaUser.id)

        return AuthResponse(
            accessToken = jwtService.generateToken(fsmaUser, extraClaims),
            tokenType = "Bearer",
            expiresIn = expiresIn,
            refreshToken = jwtService.generateToken(fsmaUser, expiresIn = 30.days),
            foodBusinessId = fsmaUser.foodBus.id,
            fsmaUserId = fsmaUser.id,
        )
    }

    // Refresh the accessToken
    fun refreshAccessToken(refreshToken: String): AuthResponse {
        // Get username from refresh token
        val email: String = jwtService.extractUsername(refreshToken)

        // Look up user details from postgres
        val fsmaUser = fsmaUserService.findByEmailIgnoreCase(email)
            ?: throw AuthorizationException("User $email not authorized")

        // Make sure that refresh token is valid & usernames match
        if (!jwtService.isTokenValid(refreshToken, fsmaUser) || !email.equals(fsmaUser.email, ignoreCase = true))
            throw AuthorizationException("Invalid user or password")

        // Embed the resellerId, clientId, userId in the token
        val extraClaims = jwtService.createExtraClaims(fsmaUser.foodBus.id, fsmaUser.id)

        return AuthResponse(
            accessToken = jwtService.generateToken(fsmaUser, extraClaims),
            tokenType = "Bearer",
            expiresIn = expiresIn,
            refreshToken = jwtService.generateToken(fsmaUser, expiresIn = 30.days),
            foodBusinessId = fsmaUser.foodBus.id,
            fsmaUserId = fsmaUser.id,
        )
    }

//    fun createPasswordResetTokenForUser(user: FsmaUser): PasswordResetToken {
//        val token = UUID.randomUUID().toString()
//        val passwordResetToken = PasswordResetToken(
//            token = token,
//            fsaUser = user
//        )
//
//        return passwordResetTokenRepository.save(passwordResetToken)
//    }
//
//    fun requestSendPasswordResetEmail(token: String, email: String): NotifyResponse {
//        // call Notification API
//        // use root admin user for generating the auth token
//        val rootFsaUser = fsaUserRepository.findAllByRolesContaining(Role.RootAdmin).firstOrNull()
//            ?: throw InternalException("Could not find a root admin user to send request for password reset email")
//
//        val extraClaims = jwtService.createExtraClaims(
//            foodBusinessId = rootFsaUser.reseller.id,
//            clientId = rootFsaUser.client.id,
//            fsaUserId = rootFsaUser.id
//        )
//        val accessToken = "Bearer ${jwtService.generateToken(rootFsaUser, extraClaims)}"
//
//        return kNotificationApiWrapper.sendPasswordResetEmail(
//            auth = accessToken,
//            requestEmailPasswordResetDto = RequestEmailPasswordResetDto(
//                email = email,
//                passwordResetToken = token
//            )
//        )
//    }
//
//    fun changeUserPassword(passwordResetToken: String, newPassword: String) {
//        // get user associated with the password reset token
//        val passwordResetTokenEntity = passwordResetTokenRepository.findFirstByToken(passwordResetToken)
//            ?: throw EntityNotFoundException("Password reset token does not exist: $passwordResetToken")
//
//        // check if token is not expired and is not yet used
//        if (OffsetDateTime.now() > passwordResetTokenEntity.expiryDate) {
//            throw BadRequestException("Password reset token is already expired")
//        }
//        if (passwordResetTokenEntity.isUsed) {
//            throw BadRequestException("Password reset token is already used")
//        }
//
//        // update user
//        val fsaUser = passwordResetTokenEntity.fsaUser
//        val updatedFsaUser = fsaUser.copy(
//            password = newPassword
//        )
//        fsaUserService.update(updatedFsaUser)
//
//        // set token to used
//        passwordResetTokenEntity.isUsed = true
//        passwordResetTokenRepository.save(passwordResetTokenEntity)
//    }
}
