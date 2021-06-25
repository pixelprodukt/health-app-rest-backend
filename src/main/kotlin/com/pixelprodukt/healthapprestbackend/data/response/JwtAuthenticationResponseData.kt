package com.pixelprodukt.healthapprestbackend.data.response

class JwtAuthenticationResponseData(
    val jwt: String,
    val tokenType: String = "Bearer"
)