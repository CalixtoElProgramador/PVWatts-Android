package com.example.pvwatts.ui.outputs

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.pvwatts.R
import com.example.pvwatts.core.Resource
import com.example.pvwatts.data.remote.ResponseFieldsDataSource
import com.example.pvwatts.databinding.FragmentOutputsBinding
import com.example.pvwatts.presentation.ResponseFieldsViewModel
import com.example.pvwatts.presentation.ResponseFieldsViewModelFactory
import com.example.pvwatts.repository.ResponseFieldsRepositoryImpl
import com.example.pvwatts.repository.RetrofitClient
import java.math.BigDecimal
import java.math.RoundingMode

class OutputsFragment : Fragment(R.layout.fragment_outputs) {

    private lateinit var binding: FragmentOutputsBinding
    private val args by navArgs<OutputsFragmentArgs>()

    private val viewModel by viewModels<ResponseFieldsViewModel> {
        ResponseFieldsViewModelFactory(
            ResponseFieldsRepositoryImpl(ResponseFieldsDataSource(RetrofitClient.webservice)),
            args.lat.toDouble(),
            args.lon.toDouble(),
            args.systemCapacity.toDouble(),
            args.azimuth.toDouble(),
            args.tilt.toDouble(),
            args.arrayType,
            args.moduleType,
            args.losses.toDouble(),
            args.dcacRatio.toDouble(),
            args.inverterEfficiency.toDouble(),
            args.groundCoverageRatio.toDouble()
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOutputsBinding.bind(view)

        viewModel.fetchMainScreenOutputs().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tableResults.tableResults.visibility = View.GONE
                    binding.tableLocation.tableLocation.visibility = View.GONE
                    binding.tableInputs.tableInputs.visibility = View.GONE

                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tableResults.tableResults.visibility = View.VISIBLE
                    binding.tableLocation.tableLocation.visibility = View.VISIBLE
                    binding.tableInputs.tableInputs.visibility = View.VISIBLE

                    val radiation: MutableList<Double> =
                        result.data.outputs.solrad_monthly.toMutableList()

                    radiation.forEachIndexed { index, value ->
                        radiation[index] =
                            BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    }

                    val acEnergy: MutableList<Double> =
                        result.data.outputs.ac_monthly.toMutableList()

                    acEnergy.forEachIndexed { index, value ->
                        acEnergy[index] =
                            BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    }

                    val dcEnergy: MutableList<Double> =
                        result.data.outputs.dc_monthly.toMutableList()

                    dcEnergy.forEachIndexed { index, value ->
                        dcEnergy[index] =
                            BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    }

                    binding.tableResults.radiationJanuary.text = radiation[0].toString()
                    binding.tableResults.radiationFebruary.text = radiation[1].toString()
                    binding.tableResults.radiationMarch.text = radiation[2].toString()
                    binding.tableResults.radiationApril.text = radiation[3].toString()
                    binding.tableResults.radiationMay.text = radiation[4].toString()
                    binding.tableResults.radiationJune.text = radiation[5].toString()
                    binding.tableResults.radiationJuly.text = radiation[6].toString()
                    binding.tableResults.radiationAugust.text = radiation[7].toString()
                    binding.tableResults.radiationSeptember.text = radiation[8].toString()
                    binding.tableResults.radiationOctober.text = radiation[9].toString()
                    binding.tableResults.radiationNovember.text = radiation[10].toString()
                    binding.tableResults.radiationDecember.text = radiation[11].toString()

                    binding.tableResults.radiationAnnual.text =
                        BigDecimal(result.data.outputs.solrad_annual).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        ).toString()

                    binding.tableResults.acEnergyJanuary.text = acEnergy[0].toString()
                    binding.tableResults.acEnergyFebruary.text = acEnergy[1].toString()
                    binding.tableResults.acEnergyMarch.text = acEnergy[2].toString()
                    binding.tableResults.acEnergyApril.text = acEnergy[3].toString()
                    binding.tableResults.acEnergyMay.text = acEnergy[4].toString()
                    binding.tableResults.acEnergyJune.text = acEnergy[5].toString()
                    binding.tableResults.acEnergyJuly.text = acEnergy[6].toString()
                    binding.tableResults.acEnergyAugust.text = acEnergy[7].toString()
                    binding.tableResults.acEnergySeptember.text = acEnergy[8].toString()
                    binding.tableResults.acEnergyOctober.text = acEnergy[9].toString()
                    binding.tableResults.acEnergyNovember.text = acEnergy[10].toString()
                    binding.tableResults.acEnergyDecember.text = acEnergy[11].toString()

                    binding.tableResults.acEnergyAnnual.text =
                        BigDecimal(result.data.outputs.ac_annual).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        ).toString()

                    binding.tableResults.dcEnergyJanuary.text = dcEnergy[0].toString()
                    binding.tableResults.dcEnergyFebruary.text = dcEnergy[1].toString()
                    binding.tableResults.dcEnergyMarch.text = dcEnergy[2].toString()
                    binding.tableResults.dcEnergyApril.text = dcEnergy[3].toString()
                    binding.tableResults.dcEnergyMay.text = dcEnergy[4].toString()
                    binding.tableResults.dcEnergyJune.text = dcEnergy[5].toString()
                    binding.tableResults.dcEnergyJuly.text = dcEnergy[6].toString()
                    binding.tableResults.dcEnergyAugust.text = dcEnergy[7].toString()
                    binding.tableResults.dcEnergySeptember.text = dcEnergy[8].toString()
                    binding.tableResults.dcEnergyOctober.text = dcEnergy[9].toString()
                    binding.tableResults.dcEnergyNovember.text = dcEnergy[10].toString()
                    binding.tableResults.dcEnergyDecember.text = dcEnergy[11].toString()

                    var dcEnergyAnnual = 0.0
                    dcEnergy.forEach {
                        dcEnergyAnnual += it
                    }

                    binding.tableResults.dcEnergyAnnual.text =
                        BigDecimal(dcEnergyAnnual).setScale(2, RoundingMode.HALF_EVEN).toString()

                    binding.tableLocation.location.text = result.data.station_info.state
                    binding.tableLocation.latitude.text = "${args.lat}°"
                    binding.tableLocation.longitude.text = "${args.lon}°"

                    binding.tableInputs.outputSystemSize.text = "${args.systemCapacity} kW"
                    binding.tableInputs.outputModuleType.text = when (args.moduleType) {
                        0 -> {
                            "Standard"
                        }
                        1 -> {
                            "Premium"
                        }
                        2 -> {
                            "Thin film"
                        }
                        else -> {
                            ""
                        }
                    }
                    binding.tableInputs.outputArrayType.text = when (args.arrayType) {
                        0 -> {
                            "Fixed - Open Rack"
                        }
                        1 -> {
                            "Fixed - Roof Mounted"
                        }
                        2 -> {
                            "1-Axis"
                        }
                        3 -> {
                            "1-Axis Backtracking"
                        }
                        4 -> {
                            "2-Axis"
                        }
                        else -> {
                            ""
                        }
                    }
                    binding.tableInputs.outputArrayTilt.text = "${args.tilt}°"
                    binding.tableInputs.outputArrayAzimuth.text = "${args.azimuth}°"
                    binding.tableInputs.outputSystemLosses.text = "${args.losses}%"
                    binding.tableInputs.outputInverterEfficiency.text =
                        "${args.inverterEfficiency}%"
                    binding.tableInputs.outputRatioSize.text = "${args.dcacRatio}"

                    val capacityFactor = BigDecimal(result.data.outputs.capacity_factor).setScale(
                        2,
                        RoundingMode.HALF_EVEN
                    )

                    binding.tableInputs.outputCapacityFactor.text =
                        "$capacityFactor%"

                }
                is Resource.Failure -> {
                    Log.d("LiveData", "${result.exception} ")
                }
            }
        })
    }
}