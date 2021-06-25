package com.pixelprodukt.healthapprestbackend.service.user

import com.pixelprodukt.healthapprestbackend.repository.user.UserRepository
import com.pixelprodukt.healthapprestbackend.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(usernameOrEmail: String?): UserDetails {
        val user = userRepository.findByNameOrEmail(usernameOrEmail!!, usernameOrEmail!!)
            .orElseThrow { UsernameNotFoundException("User not found with username or email: $usernameOrEmail") }
        return UserPrincipal.create(user)
    }

    // This method is used by JwtAuthenticationFilter
    @Transactional
    fun loadUserById(id: Long): UserDetails? {
        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found with id: $id") }
        return UserPrincipal.create(user)
    }
}