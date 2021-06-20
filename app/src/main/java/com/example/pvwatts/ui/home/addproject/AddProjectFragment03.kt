package com.example.pvwatts.ui.home.addproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentAddProject03Binding
import com.google.android.material.button.MaterialButton


class AddProjectFragment03 : Fragment(R.layout.fragment_add_project_03) {

    private lateinit var binding: FragmentAddProject03Binding
    private val viewModel: AddProjectViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getModuleSpecifications().value?.let {
            binding.inputLoadModule.setText("${it.modulePower}")
            binding.inputVoltageModule.setText("${it.moduleVoltage}")
            binding.inputCurrentModule.setText("${it.moduleCurrent}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject03Binding.bind(view)
        verifyInputs()
        backFragment()
    }

    private fun verifyInputs() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            when {
                binding.inputLoadModule.text.isNullOrEmpty() -> binding.inputLoadModule.error =
                    getString(R.string.required)
                binding.inputVoltageModule.text.isNullOrEmpty() -> binding.inputVoltageModule.error =
                    getString(R.string.required)
                binding.inputCurrentModule.text.isNullOrEmpty() -> binding.inputCurrentModule.error =
                    getString(R.string.required)
                else -> nextFragment()
            }
        }
    }

    private fun nextFragment() {
        val modulePower = binding.inputLoadModule.text.toString().toDouble()
        val moduleVoltage = binding.inputVoltageModule.text.toString().toDouble()
        val moduleCurrent = binding.inputCurrentModule.text.toString().toDouble()
        viewModel.setModuleSpecifications(
            ModuleSpecifications(
                modulePower,
                moduleVoltage,
                moduleCurrent
            )
        )
        findNavController().navigate(R.id.action_addProjectFragment03_to_addProjectFragment04)
    }

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}