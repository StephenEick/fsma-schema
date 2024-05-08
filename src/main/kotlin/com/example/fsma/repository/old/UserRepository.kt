package com.example.fsma.repository.old

import com.example.fsma.model.FsmaUser
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<FsmaUser, Long> {
    fun findByLogin(login: String): FsmaUser?
}
