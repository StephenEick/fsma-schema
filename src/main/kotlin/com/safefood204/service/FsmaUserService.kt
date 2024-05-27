package com.safefood204.service

import com.safefood204.model.FsmaUser
import com.safefood204.repository.FsmaUserRepository
import com.safefood204.util.EntityExistsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class FsmaUserService(
    private val fsmaUserRepository: FsmaUserRepository,
    private val passwordEncoder: PasswordEncoder,
) : BaseService<FsmaUser>(fsmaUserRepository, "FsmaUser") {

    override fun insert(entity: FsmaUser): FsmaUser {
        if (findByEmailIgnoreCase(entity.email) != null)
            throw EntityExistsException("User already exists: ${entity.email}")

        val finalEntity = entity.copy(
            password = passwordEncoder.encode(entity.password),
            email = entity.email.lowercase()
        )

        return super.insert(finalEntity)
    }

    fun findByEmailIgnoreCase(email: String): FsmaUser? =
        fsmaUserRepository.findByEmailIgnoreCase(email)
}
