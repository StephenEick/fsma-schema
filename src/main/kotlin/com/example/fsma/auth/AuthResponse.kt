package com.example.fsma.auth

import java.time.Duration

data class AuthResponse(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Duration,
    val refreshToken: String,
    val foodBusinessId: Long,
    val fsmaUserId: Long,
)
