package com.pixelprodukt.healthapprestbackend.data.request

import java.util.*
import javax.validation.constraints.NotNull

data class BloodPressureRequestData(
    val id: Long?,
    @NotNull val systolicValue: Short,
    @NotNull val diastolicValue: Short,
    @NotNull val pulseRate: Short,
    @NotNull val measuredAt: Date,
    val armSide: String,
    val comment: String
)
