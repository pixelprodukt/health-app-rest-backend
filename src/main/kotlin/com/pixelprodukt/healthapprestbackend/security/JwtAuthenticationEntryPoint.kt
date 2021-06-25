package com.pixelprodukt.healthapprestbackend.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse?,
        authenticationException: AuthenticationException?
    ) {
        httpServletResponse!!.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException?.message)
    }
}