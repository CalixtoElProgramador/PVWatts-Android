package com.example.pvwatts.data.model.member

data class Project(
    val battery_capacity: Double = -1.0,
    val battery_depth_discharge: Double = -1.0,
    val battery_voltage: Double = -1.0,
    val created_at: String = "",
    val days_autonomy: Int = -1,
    val image_url: String = "",
    val inverter_efficiency: Double = -1.0,
    val load_max: Double = -1.0,
    val load_month: Double = -1.0,
    val location: String = "",
    val module_current: Double = -1.0,
    val module_power: Double = -1.0,
    val module_voltage: Double = -1.0,
    val name: String = "",
    val sun_hours: Int = -1,
    val temperature: Int = -1,
    val type_project: Int =-1,
    val uid: String = ""


)