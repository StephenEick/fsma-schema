package com.safefood204.auth

data class AuthLogin(
    val email: String?,
    val password: String?,
    val refreshToken: String?,
)
