package com.example.pvwatts.repository

import com.example.pvwatts.data.model.OutputsList
import com.example.pvwatts.data.remote.OutputsDataSource

class OutputsRepositoryImpl(val dataSource: OutputsDataSource): OutputsRepository {

    override suspend fun getOutputs(): OutputsList = dataSource.getOutputs()
}