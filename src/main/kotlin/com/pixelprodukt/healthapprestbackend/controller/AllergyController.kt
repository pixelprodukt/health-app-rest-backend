package com.pixelprodukt.healthapprestbackend.controller

import com.pixelprodukt.healthapprestbackend.data.request.AllergyRequestData
import com.pixelprodukt.healthapprestbackend.data.request.PageRequestData
import com.pixelprodukt.healthapprestbackend.data.response.ApiResponseData
import com.pixelprodukt.healthapprestbackend.security.UserPrincipal
import com.pixelprodukt.healthapprestbackend.service.allergy.AllergyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/allergy")
class AllergyController(private val allergyService: AllergyService) {

    @GetMapping("/{id}")
    fun getAllergyForUserById(@PathVariable id: Long): ResponseEntity<*> {
        return ResponseEntity(allergyService.findByIdAndUserId(id), HttpStatus.OK)
    }

    @PostMapping
    fun postAllergy(@RequestBody allergyRequestData: AllergyRequestData): ResponseEntity<*> {
        allergyService.postAllergy(allergyRequestData)
        return ResponseEntity(ApiResponseData(true, "Allergy saved!"), HttpStatus.OK)
    }

    @PutMapping
    fun updateAllergy(@RequestBody allergyRequestData: AllergyRequestData): ResponseEntity<*> {
        return ResponseEntity(allergyService.updateAllergy(allergyRequestData), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteAllergy(@PathVariable id: Long): ResponseEntity<*> {
        return ResponseEntity(allergyService.deleteAllergy(id), HttpStatus.OK)
    }

    @PostMapping("/page")
    fun getPage(@RequestBody pageRequestData: PageRequestData): ResponseEntity<*> {
        return ResponseEntity(allergyService.getPage(pageRequestData.page, pageRequestData.size), HttpStatus.OK)
    }
}