package com.example.pvwatts.repository

import com.example.pvwatts.application.AppConstants
import com.example.pvwatts.data.model.OutputsList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {

    @GET("pvwatts/v6.json")
    suspend fun getOutputs(
        @Query("api_key") apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("system_capacity") systemCapacity: Double,
        @Query("azimuth") azimuth: Double,
        @Query("tilt") tilt: Double,
        @Query("array_type") arrayType: Int,
        @Query("module_type") moduleType: Int,
        @Query("losses") losses: Double
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