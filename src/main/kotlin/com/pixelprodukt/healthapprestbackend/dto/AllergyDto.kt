package com.pixelprodukt.healthapprestbackend.dto

import com.pixelprodukt.healthapprestbackend.model.allergy.Allergy

class AllergyDto(
    val id: Long,
    val description: String,
    val reaction: String
) {

    companion object {
        fun createFromEntity(allergy: Allergy): AllergyDto {
            return AllergyDto(allergy.id, allergy.description, allergy.reaction)
        }
    }
}
