package com.example.pvwatts.repository

import com.example.pvwatts.data.model.OutputsList
import com.example.pvwatts.data.remote.OutputsDataSource

class OutputsRepositoryImpl(private val dataSource: OutputsDataSource) : OutputsRepository {

    override suspend fun getOutputs(
        lat: Double,
        lon: Double,
        systemCapacity: Double,
        azimuth: Double,
        tilt: Double,
        arrayType: Int,
        moduleType: Int,
        losses: Double
    ): OutputsList =
        dataSource.getOutputs(lat, lon, systemCapacity, azimuth, tilt, arrayType, moduleType, losses)
}