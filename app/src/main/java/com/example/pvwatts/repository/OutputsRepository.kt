package com.example.pvwatts.repository

import com.example.pvwatts.data.model.OutputsList

interface OutputsRepository {

    suspend fun getOutputs(
        lat: Double,
        lon: Double,
        systemCapacity: Double,
        azimuth: Double,
        tilt: Double,
        arrayType: Int,
        moduleType: Int,
        losses: Double,
        dcacRatio: Double,
        inverterEfficiency: Double,
        groundCoverageRatio: Double

    ): OutputsList

}