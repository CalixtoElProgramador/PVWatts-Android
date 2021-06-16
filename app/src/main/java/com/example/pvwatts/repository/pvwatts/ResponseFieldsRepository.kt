package com.example.pvwatts.repository.pvwatts

import com.example.pvwatts.data.model.pvwatts.ResponseFields

interface ResponseFieldsRepository {

    suspend fun getResponseFields(
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

    ): ResponseFields

}