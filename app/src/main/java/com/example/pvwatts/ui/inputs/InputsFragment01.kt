package com.example.pvwatts.ui.inputs

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentInputs01Binding

class InputsFragment01 : Fragment(R.layout.fragment_inputs_01) {

    private lateinit var binding: FragmentInputs01Binding
    private val decimalNegative: Int =
        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL
    private val viewModel: PVWattsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputs01Binding.bind(view)

        binding.inputSystemLosses.inputType = decimalNegative

        getArgsViewModel()

        activity?.findViewById<Button>(R.id.button_next)?.setOnClickListener {
            validateInputs()
        }

    }

    private fun getArgsViewModel() {
        viewModel.getSystemInfo().value?.let {
            binding.inputSystemSize.setText("${it.systemCapacity}")
            binding.inputModuleType.setText("${it.moduleType}")
            binding.inputArrayType.setText("${it.arrayType}")
            binding.inputSystemLosses.setText("${it.losses}")
        }
    }

    private fun validateInputs() {
        when {
            binding.inputSystemSize.text.isNullOrEmpty() -> binding.inputLayoutSystemSize.error =
                getString(R.string.required)
            binding.inputSystemSize.text.toString().toDouble() > 500000.0 ||
                    binding.inputSystemSize.text.toString().toDouble() < 0.05 ->
                binding.inputLayoutSystemSize.error = getString(R.string.out_of_range)

            binding.inputModuleType.text.isNullOrEmpty() -> binding.inputLayoutModuleType.error =
                getString(R.string.required)

            binding.inputArrayType.text.isNullOrEmpty() -> binding.inputLayoutArrayType.error =
                getString(R.string.required)

            binding.inputSystemLosses.text.isNullOrEmpty() -> binding.inputLayoutSystemLosses.error =
                getString(R.string.required)
            binding.inputSystemLosses.text.toString().toDouble() > 99.0 ||
                    binding.inputSystemLosses.text.toString().toDouble() < -5.0 ->
                binding.inputLayoutSystemLosses.error = getString(R.string.out_of_range)

            else -> {
                sendParameters()
            }
        }
    }

    private fun sendParameters() {
        val systemCapacity = binding.inputSystemSize.text.toString().toDouble()
        val moduleType = binding.inputModuleType.text.toString().toInt()
        val arrayType = binding.inputArrayType.text.toString().toInt()
        val losses = binding.inputSystemLosses.text.toString().toDouble()

        viewModel.setSystemInfo(SystemInfo(systemCapacity, moduleType, arrayType, losses))

        findNavController().navigate(R.id.action_inputsFragment01_to_inputsFragment02)
    }

}