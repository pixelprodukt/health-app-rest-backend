package com.pixelprodukt.healthapprestbackend.security

import com.pixelprodukt.healthapprestbackend.service.user.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Autowired private lateinit var jwtProvider: JwtProvider
    @Autowired private lateinit var customUserDetailsService: CustomUserDetailsService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        try {
            val token = getTokenFromRequest(request)

            if (StringUtils.hasText(token) && jwtProvider.validateToken(token!!)) {

                val userId = jwtProvider.getUserIdFromJwt(token)
                val userDetails = customUserDetailsService.loadUserById(userId)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails?.authorities)

                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: Exception) {
            logger.error("Could not set user authentication in security context", ex)
        }

        chain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {

        val token = request.getHeader("Authorization")

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7, token.length)
        }

        return null
    }
}