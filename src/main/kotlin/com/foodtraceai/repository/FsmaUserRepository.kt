package com.foodtraceai.repository

import com.foodtraceai.model.FsmaUser
import org.springframework.stereotype.Repository

@Repository
interface FsmaUserRepository : BaseRepository<FsmaUser>{
    fun findByEmailIgnoreCase(email: String): FsmaUser?
}