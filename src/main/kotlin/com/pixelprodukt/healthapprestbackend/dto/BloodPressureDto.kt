package com.pixelprodukt.healthapprestbackend.dto

import com.pixelprodukt.healthapprestbackend.model.bloodpressure.BloodPressure
import java.util.*

class BloodPressureDto(
    val id: Long,
    val systolicValue: Short,
    val diastolicValue: Short,
    val pulseRate: Short,
    val measuredAt: Date,
    val armSide: String,
    val comment: String
) {

    companion object {

        fun createFromEntity(bloodPressure: BloodPressure): BloodPressureDto {
            return BloodPressureDto(
                bloodPressure.id,
                bloodPressure.systolicValue,
                bloodPressure.diastolicValue,
                bloodPressure.pulseRate,
                bloodPressure.measuredAt,
                bloodPressure.armSide,
                bloodPressure.comment
            )
        }
    }
}
