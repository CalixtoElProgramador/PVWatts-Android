package com.example.pvwatts.ui.inputs

import android.os.Bundle
import android.text.InputType
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentInputsBinding

class InputsFragment : Fragment(R.layout.fragment_inputs) {

    private lateinit var binding: FragmentInputsBinding
    private val decimalNegative: Int =
        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputsBinding.bind(view)

        expandCardView()
        binding.inputLatitude.inputType = decimalNegative
        binding.inputLongitude.inputType = decimalNegative
        binding.inputSystemLosses.inputType = decimalNegative

        setViewErrorFalseAfterChanges()


        binding.buttonCalculate.setOnClickListener {
            validateInputs()
        }

    }

    private fun validateInputs() {
        when {
            binding.inputLatitude.text.isNullOrEmpty() -> binding.inputLayoutLatitude.error =
                getString(R.string.required)
            binding.inputLatitude.text.toString().toDouble() > 90.0 ||
                    binding.inputLatitude.text.toString().toDouble() < -90.0 ->
                binding.inputLayoutLatitude.error = getString(R.string.out_of_range)

            binding.inputLongitude.text.isNullOrEmpty() -> binding.inputLayoutLongitude.error =
                getString(R.string.required)
            binding.inputLongitude.text.toString().toDouble() > 180.0 ||
                    binding.inputLongitude.text.toString().toDouble() < -180.0 ->
                binding.inputLayoutLongitude.error = getString(R.string.out_of_range)

            binding.inputSystemSize.text.isNullOrEmpty() -> binding.inputLayoutSystemSize.error =
                getString(R.string.required)
            binding.inputSystemSize.text.toString().toDouble() > 500000.0 ||
                    binding.inputSystemSize.text.toString().toDouble() < 0.05 ->
                binding.inputSystemSize.error = getString(R.string.out_of_range)

            binding.inputModuleType.text.isNullOrEmpty() -> binding.inputLayoutModuleType.error =
                getString(R.string.required)
            binding.inputArrayType.text.isNullOrEmpty() -> binding.inputLayoutArrayType.error =
                getString(R.string.required)

            binding.inputSystemLosses.text.isNullOrEmpty() -> binding.inputLayoutSystemLosses.error =
                getString(R.string.required)
            binding.inputSystemLosses.text.toString().toDouble() > 99.0 ||
                    binding.inputSystemLosses.text.toString().toDouble() < -5.0 ->
                binding.inputLayoutSystemLosses.error = getString(R.string.out_of_range)

            binding.inputTilt.text.isNullOrEmpty() -> binding.inputLayoutTilt.error =
                getString(R.string.required)
            binding.inputTilt.text.toString().toDouble() > 90.0 ->
                binding.inputLayoutTilt.error = getString(R.string.out_of_range)

            binding.inputAzimuth.text.isNullOrEmpty() -> binding.inputLayoutAzimuth.error =
                getString(R.string.required)
            binding.inputAzimuth.text.toString().toDouble() >= 360.0 ->
                binding.inputLayoutAzimuth.error = getString(R.string.out_of_range)

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

    private fun sendParameters() {
        val action = InputsFragmentDirections.actionInputsFragmentToOutputsFragment(
            binding.inputLatitude.text.toString().toFloat(),
            binding.inputLongitude.text.toString().toFloat(),
            binding.inputSystemSize.text.toString().toFloat(),
            binding.inputAzimuth.text.toString().toFloat(),
            binding.inputLatitude.text.toString().toFloat(),
            binding.inputArrayType.text.toString().toInt(),
            binding.inputModuleType.text.toString().toInt(),
            binding.inputSystemLosses.text.toString().toFloat(),
            binding.inputRatioSize.text.toString().toFloat(),
            binding.inputInverterEfficiency.text.toString().toFloat(),
            binding.inputGcr.text.toString().toFloat()
        )
        findNavController().navigate(action)
    }

    private fun setViewErrorFalseAfterChanges() {
        binding.inputLatitude.doAfterTextChanged {
            binding.inputLayoutLatitude.isErrorEnabled = false
        }
        binding.inputLongitude.doAfterTextChanged {
            binding.inputLayoutLongitude.isErrorEnabled = false
        }
        binding.inputSystemSize.doAfterTextChanged {
            binding.inputLayoutSystemSize.isErrorEnabled = false
        }
        binding.inputModuleType.doAfterTextChanged {
            binding.inputLayoutModuleType.isErrorEnabled = false
        }
        binding.inputArrayType.doAfterTextChanged {
            binding.inputLayoutArrayType.isErrorEnabled = false
        }
        binding.inputSystemLosses.doAfterTextChanged {
            binding.inputLayoutSystemLosses.isErrorEnabled = false
        }
        binding.inputTilt.doAfterTextChanged {
            binding.inputLayoutTilt.isErrorEnabled = false
        }
        binding.inputAzimuth.doAfterTextChanged {
            binding.inputLayoutAzimuth.isErrorEnabled = false
        }
        binding.inputRatioSize.doAfterTextChanged {
            binding.inputLayoutRatioSize.isErrorEnabled = false
        }
        binding.inputInverterEfficiency.doAfterTextChanged {
            binding.inputLayoutInverterEfficiency.isErrorEnabled = false
        }
        binding.inputGcr.doAfterTextChanged {
            binding.inputLayoutGcr.isErrorEnabled = false
        }
    }

}