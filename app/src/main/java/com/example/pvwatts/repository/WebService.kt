package com.example.pvwatts.repository

import com.example.pvwatts.application.AppConstants
import com.example.pvwatts.data.model.OutputsList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {

    @GET("pvwatts/v6.json?")
    suspend fun getOutputs(
        @Query("api_key")
        apiKey: String
        /*lat: Double,
        lon: Double,
        systemCapacity: Double,
        azimuth: Double,
        tilt: Double,
        array_type: Int,
        module_type: Int,
        losses: Double*/
    ): OutputsList

}

object RetrofitClient {

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }

}