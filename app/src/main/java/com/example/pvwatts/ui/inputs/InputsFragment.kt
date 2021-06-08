package com.example.pvwatts.ui.inputs

import android.os.Bundle
import android.text.InputType
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
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


        binding.buttonCalculate.setOnClickListener {
            validateInputs()
        }

    }

    private fun validateInputs() {
        when {
            binding.inputLatitude.text.isNullOrEmpty() -> binding.inputLayoutLatitude.error =
                getString(R.string.required)
            binding.inputLongitude.text.isNullOrEmpty() -> binding.inputLayoutLongitude.error =
                getString(R.string.required)
            binding.inputSystemSize.text.isNullOrEmpty() -> binding.inputLayoutSystemSize.error =
                getString(R.string.required)
            binding.inputAzimuth.text.isNullOrEmpty() -> binding.inputLayoutAzimuth.error =
                getString(R.string.required)
            binding.inputTilt.text.isNullOrEmpty() -> binding.inputLayoutTilt.error =
                getString(R.string.required)
            binding.inputArrayType.text.isNullOrEmpty() -> binding.inputLayoutArrayType.error =
                getString(R.string.required)
            binding.inputModuleType.text.isNullOrEmpty() -> binding.inputLayoutModuleType.error =
                getString(R.string.required)
            binding.inputSystemLosses.text.isNullOrEmpty() -> binding.inputLayoutSystemLosses.error =
                getString(R.string.required)
            /*binding.inputRatioSize.text.isNullOrEmpty() -> binding.inputLayoutRatioSize.error =
                getString(R.string.required)
            binding.inputInverterEfficiency.text.isNullOrEmpty() -> binding.inputLayoutInverterEfficiency.error =
                getString(R.string.required)
            binding.inputGcr.text.isNullOrEmpty() -> binding.inputLayoutGcr.error =
                getString(R.string.required)*/
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
            binding.inputSystemLosses.text.toString().toFloat()
        )
        findNavController().navigate(action)
    }

}