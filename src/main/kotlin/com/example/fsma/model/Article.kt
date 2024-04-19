package com.example.fsma.model

import com.example.fsma.util.toSlug
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Article(
    val title: String,
    val headline: String,
    val content: String,
    @ManyToOne val author: User,
    val slug: String = title.toSlug(),
    val addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue val id: Long = 0
)