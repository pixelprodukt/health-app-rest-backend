package com.pixelprodukt.healthapprestbackend.controller

import com.pixelprodukt.healthapprestbackend.config.WebSecurityConfig
import com.pixelprodukt.healthapprestbackend.dto.BloodPressureDto
import com.pixelprodukt.healthapprestbackend.mock
import com.pixelprodukt.healthapprestbackend.service.bloodpressure.BloodPressureService
import com.pixelprodukt.healthapprestbackend.service.user.CustomUserDetailsService
import com.pixelprodukt.healthapprestbackend.whenever
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*


//@WebMvcTest(BloodPressureController::class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class BloodPressureControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private val context: WebApplicationContext? = null


    private val webSecurityConfig: WebSecurityConfig = mock<WebSecurityConfig>()
    private val customUserDetailsService: CustomUserDetailsService = mock<CustomUserDetailsService>()
    private val bloodPressureService = mock<BloodPressureService>()
    private val bloodPressureController = BloodPressureController(bloodPressureService)

    private val measuredAtDateFixture = Date(1621937418L)

    private val bloodPressureDtoFixture = BloodPressureDto(
        1L,
        120,
        80,
        65,
        measuredAtDateFixture,
        "L",
        "Evening at home."
    )

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context!!)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }


    @Test
    @WithMockUser
    fun getBloodPressureByIdForUser() {

        whenever(bloodPressureService.getBloodPressureById(1L)).thenReturn(bloodPressureDtoFixture)

        val responseFixture = bloodPressureController.getBloodPressureByIdForUser(1L).body as BloodPressureDto

        assertEquals(1L, responseFixture.id)
        assertEquals(120, responseFixture.systolicValue)
        assertEquals(80, responseFixture.diastolicValue)
        assertEquals(65, responseFixture.pulseRate)
        assertEquals(Date(1621937418L), responseFixture.measuredAt)
        assertEquals("L", responseFixture.armSide)
        assertEquals("Evening at home.", responseFixture.comment)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/bloodpressure/1")
                //.with(SecurityMockMvcRequestPostProcessors.user("duke"))
                //.with(SecurityMockMvcRequestPostProcessors.jwt())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun postBloodPressure() {
    }

    @Test
    fun getPage() {
    }

    @Test
    fun getAllByDateRange() {
    }

    @Test
    fun updateBloodPressure() {
    }

    @Test
    fun deleteBloodPressure() {
    }
}