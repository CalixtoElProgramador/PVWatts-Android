package com.example.pvwatts.data.model

import java.math.BigDecimal

data class Outputs(

    val ac_monthly: List<Double> = listOf(),
    val poa_monthly: List<Double> = listOf(),
    val solrad_monthly: List<Double> = listOf(),
    val dc_monthly: List<Double> = listOf(),
    val ac_annual: Double = -1.0,
    val solrad_annual: Double = -1.0,
    val capacity_factor: Double = -1.0

)

data class OutputsList(val outputs: Outputs)