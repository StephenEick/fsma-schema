package com.foodtraceai.auth

data class AuthLogin(
    val email: String?,
    val password: String?,
    val refreshToken: String?,
)
