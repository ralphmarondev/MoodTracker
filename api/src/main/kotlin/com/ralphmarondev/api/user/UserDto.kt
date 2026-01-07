package com.ralphmarondev.api.user

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val fullName: String
)

data class RegisterResponse(
    val id: Long,
    val username: String,
    val fullName: String
)