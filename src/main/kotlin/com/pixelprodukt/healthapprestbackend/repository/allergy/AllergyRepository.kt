package com.pixelprodukt.healthapprestbackend.repository.allergy

import com.pixelprodukt.healthapprestbackend.model.allergy.Allergy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AllergyRepository : JpaRepository<Allergy, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Optional<Allergy>
    fun findAllByUserId(id: Long, pageable: Pageable): Page<Allergy>
}