package com.example.pvwatts.repository

import com.example.pvwatts.data.model.OutputsList

interface OutputsRepository {

    suspend fun getOutputs(): OutputsList

}