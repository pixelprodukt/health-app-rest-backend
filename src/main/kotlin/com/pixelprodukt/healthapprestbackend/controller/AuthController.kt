package com.pixelprodukt.healthapprestbackend.controller

import com.pixelprodukt.healthapprestbackend.data.request.AuthenticationRequestData
import com.pixelprodukt.healthapprestbackend.data.request.SignUpRequestData
import com.pixelprodukt.healthapprestbackend.data.response.ApiResponseData
import com.pixelprodukt.healthapprestbackend.data.response.JwtAuthenticationResponseData
import com.pixelprodukt.healthapprestbackend.model.user.User
import com.pixelprodukt.healthapprestbackend.repository.user.UserRepository
import com.pixelprodukt.healthapprestbackend.security.JwtProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody authRequest: AuthenticationRequestData): ResponseEntity<*> {

        try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authRequest.nameOrEmail, authRequest.password)
            )

            SecurityContextHolder.getContext().authentication = authentication
            val jwt = jwtProvider.generateToken(authentication)

            return ResponseEntity(JwtAuthenticationResponseData(jwt), HttpStatus.OK)

        } catch (error: AuthenticationException) {
            return ResponseEntity(JwtAuthenticationResponseData(""), HttpStatus.OK)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequestData): ResponseEntity<*> {

        if (signUpRequest.name.isNullOrEmpty() && signUpRequest.email.isNullOrEmpty()) {
            return ResponseEntity(ApiResponseData(false, "No name and email."), HttpStatus.BAD_REQUEST)
        }

        if (userRepository.existsByNameOrEmail(signUpRequest.name, signUpRequest.email)) {
            return ResponseEntity(ApiResponseData(false, "Name or email already taken."), HttpStatus.BAD_REQUEST)
        }

        val user = User(
            0,
            signUpRequest.name!!,
            signUpRequest.email!!,
            passwordEncoder.encode(signUpRequest.password)
        )

        val result = userRepository.save(user)

        return ResponseEntity(ApiResponseData(true, "User was created and saved."), HttpStatus.OK)
    }
}