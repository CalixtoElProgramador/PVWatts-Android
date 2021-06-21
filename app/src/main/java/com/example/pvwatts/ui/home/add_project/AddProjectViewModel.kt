package com.example.pvwatts.ui.home.add_project

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddProjectViewModel : ViewModel() {

    private val typeProject = MutableLiveData<Int>()
    private val projectDemand = MutableLiveData<ProjectDemand>()
    private val weatherConditions = MutableLiveData<WeatherConditions>()
    private val moduleSpecifications = MutableLiveData<ModuleSpecifications>()
    private val inverterEfficiency = MutableLiveData<Double>()
    private val batterySpecifications = MutableLiveData<BatterySpecifications>()
    private val projectDescription = MutableLiveData<ProjectDescription>()

    fun setTypeProject(type: Int) {
        typeProject.value = type
    }

    fun getTypeProject(): LiveData<Int> = typeProject

    fun setProjectDemand(demandProject: ProjectDemand) {
        projectDemand.value = demandProject
    }

    fun getProjectDemand(): LiveData<ProjectDemand> = projectDemand

    fun setWeatherConditions(conditionsWeather: WeatherConditions) {
        weatherConditions.value = conditionsWeather
    }

    fun getWeatherConditions(): LiveData<WeatherConditions> = weatherConditions

    fun setModuleSpecifications(specifications: ModuleSpecifications) {
        moduleSpecifications.value = specifications
    }

    fun getModuleSpecifications(): LiveData<ModuleSpecifications> = moduleSpecifications

    fun setInverterEfficiency(efficiency: Double) {
        inverterEfficiency.value = efficiency
    }

    fun getInverterEfficiency(): LiveData<Double> = inverterEfficiency

    fun setBatterySpecifications(specifications: BatterySpecifications) {
        batterySpecifications.value = specifications
    }

    fun getBatterySpecifications(): LiveData<BatterySpecifications> = batterySpecifications

    fun setProjectDescription(description: ProjectDescription) {
        projectDescription.value = description
    }

    fun getProjectDescription(): LiveData<ProjectDescription> = projectDescription

}

data class ProjectDemand(val load: Double, val loadMax: Double)
data class WeatherConditions(val sunHours: Int, val temperature: Int)
data class ModuleSpecifications(
    val modulePower: Double,
    val moduleVoltage: Double,
    val moduleCurrent: Double
)

data class BatterySpecifications(
    val daysAutonomy: Int,
    val depthDischarge: Double,
    val batteryCapacity: Double,
    val batteryVoltage: Double
)

data class ProjectDescription(val name: String, val location: String, val bitmap: Bitmap)