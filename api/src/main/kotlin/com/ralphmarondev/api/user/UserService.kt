package com.ralphmarondev.api.user

import com.ralphmarondev.api.config.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {
    fun register(request: RegisterRequest): RegisterResponse {
        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password) ?: "",
            fullName = request.fullName
        )
        val saved = userRepository.save(user)
        return saved.toResponse()
    }

    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUsername(request.username)
            .orElseThrow { IllegalArgumentException("Invalid credentials") }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }
        val accessToken = jwtService.generateAccessToken(
            userId = user.id,
            username = user.username
        )
        val refreshToken = jwtService.generateRefreshToken(
            userId = user.id,
            username = user.username
        )
        return LoginResponse(accessToken = accessToken, refreshToken = refreshToken)
    }
}