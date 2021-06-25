package com.pixelprodukt.healthapprestbackend.controller

import com.pixelprodukt.healthapprestbackend.data.request.BloodPressureRequestData
import com.pixelprodukt.healthapprestbackend.data.request.DateRangeRequestData
import com.pixelprodukt.healthapprestbackend.data.request.PageRequestData
import com.pixelprodukt.healthapprestbackend.data.response.ApiResponseData
import com.pixelprodukt.healthapprestbackend.repository.bloodpressure.BloodPressureRepository
import com.pixelprodukt.healthapprestbackend.service.bloodpressure.BloodPressureService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/bloodpressure")
class BloodPressureController(
    private val bloodPressureService: BloodPressureService
) {

    @GetMapping("/{id}")
    fun getBloodPressureByIdForUser(@PathVariable id: Long): ResponseEntity<*> {
        return ResponseEntity(bloodPressureService.getBloodPressureById(id), HttpStatus.OK)
    }

    @PostMapping
    fun postBloodPressure(@Valid @RequestBody bloodPressureRequestData: BloodPressureRequestData): ResponseEntity<*> {
        bloodPressureService.postBloodPressure(bloodPressureRequestData)
        return ResponseEntity(ApiResponseData(true, "Blood pressure values saved!"), HttpStatus.OK)
    }

    @PostMapping("/page")
    fun getPage(@Valid @RequestBody pageRequestData: PageRequestData): ResponseEntity<*> {
        return ResponseEntity(bloodPressureService.getPage(pageRequestData.page, pageRequestData.size), HttpStatus.OK)
    }

    @PostMapping("/date-range")
    fun getAllByDateRange(@Valid @RequestBody rangeRequest: DateRangeRequestData): ResponseEntity<*> {
        val result = bloodPressureService.findAllByMeasuredAtBetween(rangeRequest.startDate, rangeRequest.endDate)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PutMapping
    fun updateBloodPressure(@Valid @RequestBody bloodPressureRequestData: BloodPressureRequestData): ResponseEntity<*> {
        return ResponseEntity(bloodPressureService.updateBloodPressure(bloodPressureRequestData), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteBloodPressure(@PathVariable id: Long): ResponseEntity<*> {
        return ResponseEntity(bloodPressureService.deleteBloodPressure(id), HttpStatus.OK)
    }
}