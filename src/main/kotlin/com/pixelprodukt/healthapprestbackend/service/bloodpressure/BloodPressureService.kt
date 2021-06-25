package com.pixelprodukt.healthapprestbackend.service.bloodpressure

import com.pixelprodukt.healthapprestbackend.data.request.BloodPressureRequestData
import com.pixelprodukt.healthapprestbackend.data.response.ApiResponseData
import com.pixelprodukt.healthapprestbackend.dto.BloodPressureDto
import com.pixelprodukt.healthapprestbackend.model.bloodpressure.BloodPressure
import com.pixelprodukt.healthapprestbackend.repository.bloodpressure.BloodPressureRepository
import com.pixelprodukt.healthapprestbackend.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class BloodPressureService(private val bloodPressureRepository: BloodPressureRepository) {

    fun getBloodPressureById(id: Long): BloodPressureDto {
        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return BloodPressureDto.createFromEntity(bloodPressureRepository.findByIdAndUserId(id, user.id).get())
    }

    fun getPage(page: Int, size: Int): Page<BloodPressureDto> {

        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        return bloodPressureRepository.findAllByUserId(user.id, PageRequest.of(page, size, Sort.by("measuredAt").descending()))
            .map { entity -> BloodPressureDto.createFromEntity(entity) }
    }

    fun postBloodPressure(bloodPressureRequestData: BloodPressureRequestData): ApiResponseData {

        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        val bloodPressure = BloodPressure(
            0,
            user.id,
            bloodPressureRequestData.systolicValue,
            bloodPressureRequestData.diastolicValue,
            bloodPressureRequestData.pulseRate,
            bloodPressureRequestData.measuredAt,
            bloodPressureRequestData.armSide,
            bloodPressureRequestData.comment
        )

        bloodPressureRepository.save(bloodPressure)
        return ApiResponseData(true, "Blood Pressure was saved!")
    }

    fun updateBloodPressure(bloodPressureRequestData: BloodPressureRequestData): ApiResponseData {

        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        if (bloodPressureRequestData.id == null) {
            return ApiResponseData(false, "No id for blood pressure data! Update canceled!")
        }

        val bloodPressure = BloodPressure(
            bloodPressureRequestData.id,
            user.id,
            bloodPressureRequestData.systolicValue,
            bloodPressureRequestData.diastolicValue,
            bloodPressureRequestData.pulseRate,
            bloodPressureRequestData.measuredAt,
            bloodPressureRequestData.armSide,
            bloodPressureRequestData.comment
        )

        bloodPressureRepository.save(bloodPressure)
        return ApiResponseData(true, "Blood pressure data was updated!")
    }

    fun deleteBloodPressure(id: Long): ApiResponseData {

        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val bloodPressureEntity = bloodPressureRepository.findById(id).get()

        return if (bloodPressureEntity.userId == user.id) {
            bloodPressureRepository.delete(bloodPressureEntity)
            ApiResponseData(true, "Blood pressure data was deleted!")
        } else {
            ApiResponseData(false, "User is not authorized to delete blood pressure with this id!")
        }
    }

    fun findAllByMeasuredAtBetween(startDate: Date, endDate: Date): List<BloodPressureDto> {

        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        val resultList = bloodPressureRepository.findAllByMeasuredAtBetweenAndUserId(startDate, endDate, user.id)
        val mutableBloodPressureDtoList = mutableListOf<BloodPressureDto>()

        resultList.forEach { bloodPressure -> mutableBloodPressureDtoList.add(BloodPressureDto.createFromEntity(bloodPressure)) }

        return mutableBloodPressureDtoList.toList()
    }
}