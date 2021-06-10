package com.example.pvwatts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.pvwatts.core.Resource
import com.example.pvwatts.repository.OutputsRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class OutputsViewModel(
    private val repo: OutputsRepository,
    private val lat: Double,
    private val lon: Double,
    private val systemCapacity: Double,
    private val azimuth: Double,
    private val tilt: Double,
    private val arrayType: Int,
    private val moduleType: Int,
    private val losses: Double,
    private val dcacRatio: Double,
    private val inverterEfficiency: Double,
    private val groundCoverageRatio: Double
) : ViewModel() {

    fun fetchMainScreenOutputs() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(
                Resource.Success(
                    repo.getOutputs(
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
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class OutputsViewModelFactory(
    private val repo: OutputsRepository,
    private val lat: Double,
    private val lon: Double,
    private val systemCapacity: Double,
    private val azimuth: Double,
    private val tilt: Double,
    private val arrayType: Int,
    private val moduleType: Int,
    private val losses: Double,
    private val dcacRatio: Double,
    private val inverterEfficiency: Double,
    private val groundCoverageRatio: Double

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            OutputsRepository::class.java,
            Double::class.java,
            Double::class.java,
            Double::class.java,
            Double::class.java,
            Double::class.java,
            Int::class.java,
            Int::class.java,
            Double::class.java,
            Double::class.java,
            Double::class.java,
            Double::class.java
        )
            .newInstance(
                repo,
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
}