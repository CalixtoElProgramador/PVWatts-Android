package com.example.pvwatts.ui.inputs

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

class PVWattsViewModel : ViewModel() {

    private var locationData = MutableLiveData<Location>()
    private val systemInfoData = MutableLiveData<SystemInfo>()
    private val advancedParametersData = MutableLiveData<AdvancedParameters>()

    fun setLocation(location: Location) {
        locationData.value = location
    }

    fun setSystemInfo(systemInfo: SystemInfo) {
        systemInfoData.value = systemInfo
    }

    fun setAdvancedParameters(advancedParameters: AdvancedParameters) {
        advancedParametersData.value = advancedParameters
    }

    fun getLocation(): LiveData<Location> = locationData

    fun getSystemInfo(): LiveData<SystemInfo> = systemInfoData

    fun getAdvancedParameters(): LiveData<AdvancedParameters> = advancedParametersData

}

@Parcelize
data class Location(val lat: Double, val lon: Double) : Parcelable

@Parcelize
data class SystemInfo(
    val systemCapacity: Double,
    val moduleType: Int,
    val arrayType: Int,
    val losses: Double
) : Parcelable

@Parcelize
data class AdvancedParameters(
    val azimuth: Double,
    val tilt: Double,
    val dcacRatio: Double,
    val inverterEfficiency: Double,
    val groundCoverageRatio: Double
): Parcelable