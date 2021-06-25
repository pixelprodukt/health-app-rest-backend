package com.pixelprodukt.healthapprestbackend.repository.bloodpressure

import com.pixelprodukt.healthapprestbackend.model.bloodpressure.BloodPressure
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BloodPressureRepository : JpaRepository<BloodPressure, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Optional<BloodPressure>
    fun findAllByUserId(id: Long, pageable: Pageable): Page<BloodPressure>
    fun findAllByMeasuredAtBetweenAndUserId(startDate: Date, endDate: Date, userId: Long): MutableList<BloodPressure>
}