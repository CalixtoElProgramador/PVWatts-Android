package com.example.pvwatts.data.remote.pvwatts

import com.example.pvwatts.application.AppConstants
import com.example.pvwatts.data.model.pvwatts.ResponseFields
import com.example.pvwatts.repository.pvwatts.WebService

class ResponseFieldsDataSource(private val webService: WebService) {

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

    ): ResponseFields = webService.getResponseFields(
        AppConstants.API_KEY,
        lat,
        lon,
        systemCapacity,
        azimuth,
        tilt,
        arrayType,
        moduleType,
        losses,
        dcacRatio,
        inverterEfficiency,
        groundCoverageRatio
    )

}