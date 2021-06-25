package com.pixelprodukt.healthapprestbackend.repository.user

import com.pixelprodukt.healthapprestbackend.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    override fun findById(id: Long): Optional<User>
    override fun findAll(): MutableList<User>
    fun findByNameOrEmail(name: String, email: String): Optional<User>
    fun existsByNameOrEmail(name: String?, email: String?): Boolean
}