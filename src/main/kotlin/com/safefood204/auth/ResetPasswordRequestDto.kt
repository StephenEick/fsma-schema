package com.safefood204.auth

data class ResetPasswordRequestDto(
    val email: String
)

data class RequestEmailPasswordResetDto(
    val email: String,
    val passwordResetToken: String
)

data class ChangePasswordDto(
    val passwordResetToken: String,
    val newPassword: String
)
