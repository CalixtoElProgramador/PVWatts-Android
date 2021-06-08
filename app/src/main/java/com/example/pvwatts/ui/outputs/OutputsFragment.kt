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
import com.example.pvwatts.data.remote.OutputsDataSource
import com.example.pvwatts.databinding.FragmentOutputsBinding
import com.example.pvwatts.presentation.OutputsViewModel
import com.example.pvwatts.presentation.OutputsViewModelFactory
import com.example.pvwatts.repository.OutputsRepositoryImpl
import com.example.pvwatts.repository.RetrofitClient
import java.math.BigDecimal
import java.math.RoundingMode

class OutputsFragment : Fragment(R.layout.fragment_outputs) {

    private lateinit var binding: FragmentOutputsBinding
    private val args by navArgs<OutputsFragmentArgs>()

    private val viewModel by viewModels<OutputsViewModel> {
        OutputsViewModelFactory(
            OutputsRepositoryImpl(OutputsDataSource(RetrofitClient.webservice)),
            args.lat.toDouble(),
            args.lon.toDouble(),
            args.systemCapacity.toDouble(),
            args.azimuth.toDouble(),
            args.tilt.toDouble(),
            args.arrayType,
            args.moduleType,
            args.losses.toDouble()
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
                    binding.tableResults.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tableResults.visibility = View.VISIBLE

                    val radiation: MutableList<Double> =
                        result.data.outputs.solrad_monthly.toMutableList()

                    radiation.forEachIndexed { index, value ->
                        radiation[index] =
                            BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    }

                    binding.radiationJanuary.text = radiation[0].toString()
                    binding.radiationFebruary.text = radiation[1].toString()
                    binding.radiationMarch.text = radiation[2].toString()
                    binding.radiationApril.text = radiation[3].toString()
                    binding.radiationMay.text = radiation[4].toString()
                    binding.radiationJune.text = radiation[5].toString()
                    binding.radiationJuly.text = radiation[6].toString()
                    binding.radiationAugust.text = radiation[7].toString()
                    binding.radiationSeptember.text = radiation[8].toString()
                    binding.radiationOctober.text = radiation[9].toString()
                    binding.radiationNovember.text = radiation[10].toString()
                    binding.radiationDecember.text = radiation[11].toString()

                    binding.radiationAnnual.text =
                        BigDecimal(result.data.outputs.solrad_annual).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        ).toString()

                    val acEnergy: MutableList<Double> =
                        result.data.outputs.ac_monthly.toMutableList()

                    acEnergy.forEachIndexed { index, value ->
                        acEnergy[index] =
                            BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    }

                    binding.acEnergyJanuary.text = acEnergy[0].toString()
                    binding.acEnergyFebruary.text = acEnergy[1].toString()
                    binding.acEnergyMarch.text = acEnergy[2].toString()
                    binding.acEnergyApril.text = acEnergy[3].toString()
                    binding.acEnergyMay.text = acEnergy[4].toString()
                    binding.acEnergyJune.text = acEnergy[5].toString()
                    binding.acEnergyJuly.text = acEnergy[6].toString()
                    binding.acEnergyAugust.text = acEnergy[7].toString()
                    binding.acEnergySeptember.text = acEnergy[8].toString()
                    binding.acEnergyOctober.text = acEnergy[9].toString()
                    binding.acEnergyNovember.text = acEnergy[10].toString()
                    binding.acEnergyDecember.text = acEnergy[11].toString()

                    binding.acEnergyAnnual.text =
                        BigDecimal(result.data.outputs.ac_annual).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        ).toString()

                    val dcEnergy: MutableList<Double> =
                        result.data.outputs.dc_monthly.toMutableList()

                    dcEnergy.forEachIndexed { index, value ->
                        dcEnergy[index] =
                            BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    }

                    binding.dcEnergyJanuary.text = dcEnergy[0].toString()
                    binding.dcEnergyFebruary.text = dcEnergy[1].toString()
                    binding.dcEnergyMarch.text = dcEnergy[2].toString()
                    binding.dcEnergyApril.text = dcEnergy[3].toString()
                    binding.dcEnergyMay.text = dcEnergy[4].toString()
                    binding.dcEnergyJune.text = dcEnergy[5].toString()
                    binding.dcEnergyJuly.text = dcEnergy[6].toString()
                    binding.dcEnergyAugust.text = dcEnergy[7].toString()
                    binding.dcEnergySeptember.text = dcEnergy[8].toString()
                    binding.dcEnergyOctober.text = dcEnergy[9].toString()
                    binding.dcEnergyNovember.text = dcEnergy[10].toString()
                    binding.dcEnergyDecember.text = dcEnergy[11].toString()

                    var dcEnergyAnnual = 0.0
                    dcEnergy.forEach {
                        dcEnergyAnnual += it
                    }

                    binding.dcEnergyAnnual.text =
                        BigDecimal(dcEnergyAnnual).setScale(2, RoundingMode.HALF_EVEN).toString()

                }
                is Resource.Failure -> {
                    Log.d("LiveData", "${result.exception} ")
                }
            }
        })

    }

}