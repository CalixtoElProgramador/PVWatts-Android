package com.example.pvwatts.ui.inputs

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentInputs02Binding

class InputsFragment02 : Fragment(R.layout.fragment_inputs_02) {

    private lateinit var binding: FragmentInputs02Binding
    private val viewModel: PVWattsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputs02Binding.bind(view)
        expandCardView()

        getArgsViewModel()

        activity?.findViewById<Button>(R.id.button_next)?.setOnClickListener {
            validateInputs()
        }

    }

    private fun validateInputs() {
        when {

            binding.inputAzimuth.text.isNullOrEmpty() -> binding.inputLayoutAzimuth.error =
                getString(R.string.required)
            binding.inputAzimuth.text.toString().toDouble() >= 360.0 ->
                binding.inputLayoutAzimuth.error = getString(R.string.out_of_range)

            binding.inputTilt.text.isNullOrEmpty() -> binding.inputLayoutTilt.error =
                getString(R.string.required)
            binding.inputTilt.text.toString().toDouble() > 90.0 ->
                binding.inputLayoutTilt.error = getString(R.string.out_of_range)

            binding.inputRatioSize.text.isNullOrEmpty() -> binding.inputLayoutRatioSize.error =
                getString(R.string.required)

            binding.inputInverterEfficiency.text.isNullOrEmpty() -> binding.inputLayoutInverterEfficiency.error =
                getString(R.string.required)
            binding.inputInverterEfficiency.text.toString().toDouble() > 99.5 ||
                    binding.inputInverterEfficiency.text.toString().toDouble() < 90.0 ->
                binding.inputLayoutInverterEfficiency.error = getString(R.string.out_of_range)

            binding.inputGcr.text.isNullOrEmpty() -> binding.inputLayoutGcr.error =
                getString(R.string.required)
            binding.inputGcr.text.toString().toDouble() > 3.0 ->
                binding.inputLayoutGcr.error = getString(R.string.out_of_range)

            else -> {
                sendParameters()
            }
        }
    }

    private fun sendParameters() {

        val azimuth = binding.inputAzimuth.text.toString().toDouble()
        val tilt = binding.inputTilt.text.toString().toDouble()
        val dcacRatio = binding.inputRatioSize.text.toString().toDouble()
        val inverterEfficiency = binding.inputInverterEfficiency.text.toString().toDouble()
        val groundCoverageRatio = binding.inputGcr.text.toString().toDouble()

        viewModel.setAdvancedParameters(
            AdvancedParameters(
                azimuth,
                tilt,
                dcacRatio,
                inverterEfficiency,
                groundCoverageRatio
            )
        )

        viewModel.getLocation().value?.let { location ->
            viewModel.getSystemInfo().value?.let { systemInfo ->
                viewModel.getAdvancedParameters().value?.let { advancedParameters ->
                    val action = InputsFragment02Directions.actionInputsFragment02ToOutputsFragment(
                        location.lat.toFloat(),
                        location.lon.toFloat(),
                        systemInfo.systemCapacity.toFloat(),
                        advancedParameters.azimuth.toFloat(),
                        advancedParameters.tilt.toFloat(),
                        systemInfo.arrayType,
                        systemInfo.moduleType,
                        systemInfo.losses.toFloat(),
                        advancedParameters.dcacRatio.toFloat(),
                        advancedParameters.inverterEfficiency.toFloat(),
                        advancedParameters.groundCoverageRatio.toFloat()
                    )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun expandCardView() {
        binding.buttonExpandCardView.setOnClickListener {
            if (binding.constraintLayoutCardExpandable.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    binding.cardAdvancedParameters,
                    AutoTransition()
                )
                binding.constraintLayoutCardExpandable.visibility = View.VISIBLE
                binding.buttonExpandCardView.setBackgroundResource(R.drawable.ic_arrow_up)
            } else {
                TransitionManager.beginDelayedTransition(
                    binding.cardAdvancedParameters,
                    AutoTransition()
                )
                binding.constraintLayoutCardExpandable.visibility = View.GONE
                binding.buttonExpandCardView.setBackgroundResource(R.drawable.ic_arrow_down)
            }
        }
    }

    private fun getArgsViewModel() {
        viewModel.getAdvancedParameters().value?.let {
            binding.inputAzimuth.setText("${it.azimuth}")
            binding.inputTilt.setText("${it.tilt}")
            binding.inputRatioSize.setText("${it.dcacRatio}")
            binding.inputInverterEfficiency.setText("${it.inverterEfficiency}")
            binding.inputGcr.setText("${it.groundCoverageRatio}")
        }
    }

}
