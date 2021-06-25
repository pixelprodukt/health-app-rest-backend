package com.pixelprodukt.healthapprestbackend.service.allergy

import com.pixelprodukt.healthapprestbackend.data.request.AllergyRequestData
import com.pixelprodukt.healthapprestbackend.data.request.PageRequestData
import com.pixelprodukt.healthapprestbackend.data.response.ApiResponseData
import com.pixelprodukt.healthapprestbackend.dto.AllergyDto
import com.pixelprodukt.healthapprestbackend.model.allergy.Allergy
import com.pixelprodukt.healthapprestbackend.repository.allergy.AllergyRepository
import com.pixelprodukt.healthapprestbackend.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AllergyService(private val allergyRepository: AllergyRepository) {

    fun getPage(page: Int, size: Int): Page<AllergyDto> {
        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return allergyRepository.findAllByUserId(user.id, PageRequest.of(page, size, Sort.by("createdAt").descending()))
            .map { entity -> AllergyDto.createFromEntity(entity) }
    }

    fun findByIdAndUserId(id: Long): AllergyDto {
        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return AllergyDto.createFromEntity(allergyRepository.findByIdAndUserId(id, user.id).get())
    }

    fun postAllergy(allergyRequestData: AllergyRequestData): ApiResponseData {

        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        val allergy = Allergy(
            0,
            user.id,
            allergyRequestData.description,
            allergyRequestData.reaction
        )

        allergyRepository.save(allergy)
        return ApiResponseData(true, "Allergy was saved!")
    }

    fun updateAllergy(allergyRequestData: AllergyRequestData): ApiResponseData {

        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        if (allergyRequestData.id == null) {
            return ApiResponseData(false, "Id is missing for allergy data!")
        }

        val allergy = Allergy(
            allergyRequestData.id,
            user.id,
            allergyRequestData.description,
            allergyRequestData.reaction
        )

        allergyRepository.save(allergy)
        return ApiResponseData(true, "Allergy was updated!")
    }

    fun deleteAllergy(id: Long): ApiResponseData {
        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val allergy = allergyRepository.findByIdAndUserId(id, user.id).get()

        return if (allergy.userId == user.id) {
            allergyRepository.delete(allergy)
            ApiResponseData(true, "Allergy was deleted!")
        } else {
            ApiResponseData(false, "User not authorized to delete allergy!")
        }
    }
}