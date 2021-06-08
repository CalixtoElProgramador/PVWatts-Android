package com.example.pvwatts.data.remote

import com.example.pvwatts.application.AppConstants
import com.example.pvwatts.data.model.OutputsList
import com.example.pvwatts.repository.WebService

class OutputsDataSource(private val webService: WebService) {

    suspend fun getOutputs(): OutputsList = webService.getOutputs(AppConstants.API_KEY)

}