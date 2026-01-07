package com.ralphmarondev.api.user

fun User.toResponse(): RegisterResponse {
    return RegisterResponse(
        id = id,
        username = username,
        fullName = fullName
    )
}