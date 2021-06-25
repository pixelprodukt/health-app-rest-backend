package com.pixelprodukt.healthapprestbackend.data.request

import javax.validation.constraints.NotBlank

data class SignUpRequestData(
    val name: String?,
    val email: String?,
    @NotBlank val password: String
)