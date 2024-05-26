package com.example.fsma.repository

import com.example.fsma.model.FsmaUser
import org.springframework.stereotype.Repository

@Repository
interface FsmaUserRepository : BaseRepository<FsmaUser>{
    fun findByEmailIgnoreCase(email: String): FsmaUser?
}