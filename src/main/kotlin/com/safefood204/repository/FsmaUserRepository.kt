package com.safefood204.repository

import com.safefood204.model.FsmaUser
import org.springframework.stereotype.Repository

@Repository
interface FsmaUserRepository : BaseRepository<FsmaUser>{
    fun findByEmailIgnoreCase(email: String): FsmaUser?
}