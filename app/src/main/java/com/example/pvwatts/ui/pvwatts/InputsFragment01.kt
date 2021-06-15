package com.example.pvwatts.ui.pvwatts

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentInputs01Binding
import com.google.android.material.button.MaterialButton

class InputsFragment01 : Fragment(R.layout.fragment_inputs_01) {

    private lateinit var binding: FragmentInputs01Binding
    private val decimalNegative: Int =
        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL
    private val viewModel: PVWattsViewModel by activityViewModels()
    private var moduleType: Int = 0
    private var arrayType: Int = 0

    override fun onResume() {
        super.onResume()
        val moduleTypes = resources.getStringArray(R.array.module_type)
        val arrayTypes = resources.getStringArray(R.array.array_type)

        val adapterModules = ArrayAdapter(requireContext(), R.layout.list_item, moduleTypes)
        val adapterArrays = ArrayAdapter(requireContext(), R.layout.list_item, arrayTypes)

        binding.inputModuleType.setAdapter(adapterModules)
        binding.inputArrayType.setAdapter(adapterArrays)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputs01Binding.bind(view)
        binding.inputSystemLosses.inputType = decimalNegative

        getArgsViewModel()
        setViewErrorFalseAfterChanges()
        setPositionItemDropMenu()

        activity?.findViewById<Button>(R.id.button_next)?.setOnClickListener {
            validateInputs()
        }

        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun setPositionItemDropMenu() {
        binding.inputModuleType.setOnItemClickListener { _, _, position, _ ->
            setModuleType(position)
        }

        binding.inputArrayType.setOnItemClickListener { _, _, position, _ ->
            setArrayType(position)
        }
    }

    private fun setViewErrorFalseAfterChanges() {
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
    }

    private fun getArgsViewModel() {
        viewModel.getSystemInfo().value?.let {
            binding.inputSystemSize.setText("${it.systemCapacity}")

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
        val losses = binding.inputSystemLosses.text.toString().toDouble()

        viewModel.setSystemInfo(SystemInfo(systemCapacity, getModuleType(), getArrayType(), losses))

        findNavController().navigate(R.id.action_inputsFragment01_to_inputsFragment02)
    }

    private fun setModuleType(position: Int) {
        moduleType = position
    }

    private fun getModuleType(): Int = moduleType

    private fun setArrayType(position: Int) {
        arrayType = position
    }

    private fun getArrayType(): Int = arrayType

}