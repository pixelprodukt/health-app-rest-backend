package com.pixelprodukt.healthapprestbackend.data.request

import javax.validation.constraints.NotBlank

data class AuthenticationRequestData(
    @NotBlank val nameOrEmail: String,
    @NotBlank val password: String
)