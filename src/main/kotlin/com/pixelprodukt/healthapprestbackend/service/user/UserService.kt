package com.pixelprodukt.healthapprestbackend.service.user

import com.pixelprodukt.healthapprestbackend.model.user.User
import com.pixelprodukt.healthapprestbackend.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): MutableList<User> {
        return userRepository.findAll()
    }
}