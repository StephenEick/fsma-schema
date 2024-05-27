package com.safefood204.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T> : JpaRepository<T, Long> {
    fun dateDeletedIsNull(): List<T>
    override fun findAll(): List<T> {
        return dateDeletedIsNull()
    }
}
