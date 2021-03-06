package com.example.pvwatts.repository.pvwatts

import com.example.pvwatts.data.model.pvwatts.ResponseFields
import com.example.pvwatts.data.remote.pvwatts.ResponseFieldsDataSource

class ResponseFieldsRepositoryImpl(private val dataSource: ResponseFieldsDataSource) :
    ResponseFieldsRepository {

    override suspend fun getResponseFields(
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

    ): ResponseFields =
        dataSource.getResponseFields(
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