package com.pixelprodukt.healthapprestbackend.security

import com.fasterxml.jackson.annotation.JsonIgnore
import com.pixelprodukt.healthapprestbackend.model.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserPrincipal(
    val id: Long,
    private val name: String,
    @JsonIgnore private val email: String,
    @JsonIgnore private val password: String,
    private val authorities: MutableCollection<out GrantedAuthority>
) : UserDetails {

    companion object {

        fun create(user: User): UserPrincipal {
            val authorities: MutableCollection<out GrantedAuthority> = Collections.singletonList(SimpleGrantedAuthority("User"))

            return UserPrincipal(
                user.id,
                user.name,
                user.email,
                user.password,
                authorities
            )
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities
    override fun getPassword(): String = password
    override fun getUsername(): String = name
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}