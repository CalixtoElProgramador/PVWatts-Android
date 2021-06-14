package com.example.pvwatts.ui.inputs

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentInputs00Binding
import com.google.android.material.button.MaterialButton

class InputsFragment00 : Fragment(R.layout.fragment_inputs_00) {

    private lateinit var binding: FragmentInputs00Binding
    private val decimalNegative: Int =
        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL

    private val viewModel: PVWattsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputs00Binding.bind(view)

        binding.inputLatitude.inputType = decimalNegative
        binding.inputLongitude.inputType = decimalNegative

        getArgsViewModel()

        setViewErrorFalseAfterChanges()

        activity?.findViewById<Button>(R.id.button_next)?.setOnClickListener {
            validateInputs()
        }

        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun getArgsViewModel() {
        viewModel.getLocation().value?.let {
            binding.inputLatitude.setText("${it.lat}")
            binding.inputLongitude.setText("$it")
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

            else -> {
                sendParameters()
            }
        }
    }

    private fun sendParameters() {

        val lat = binding.inputLatitude.text.toString().toDouble()
        val lon = binding.inputLongitude.text.toString().toDouble()

        viewModel.setLocation(Location(lat, lon))

        findNavController().navigate(R.id.action_inputsFragment_to_inputsFragment01)

    }

    private fun setViewErrorFalseAfterChanges() {
        binding.inputLatitude.doAfterTextChanged {
            binding.inputLayoutLatitude.isErrorEnabled = false
        }
        binding.inputLongitude.doAfterTextChanged {
            binding.inputLayoutLongitude.isErrorEnabled = false
        }
    }
}