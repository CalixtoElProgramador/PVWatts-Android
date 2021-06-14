package com.example.pvwatts.data.model

data class StationInfo(
    val lat: Double,
    val lon: Double,
    val elev: Double,
    val tz: Double,
    val location: String,
    val city: String,
    val state: String,
    val solar_resource_file: String,
    val distance: String
)