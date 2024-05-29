// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.auth

import java.time.Duration

data class AuthResponse(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Duration,
    val refreshToken: String,
    val foodBusinessId: Long,
    val fsmaUserId: Long,
)
