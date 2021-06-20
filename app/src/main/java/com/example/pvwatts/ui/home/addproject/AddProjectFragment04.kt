package com.example.pvwatts.ui.home.addproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentAddProject04Binding
import com.google.android.material.button.MaterialButton

class AddProjectFragment04 : Fragment(R.layout.fragment_add_project_04) {

    private lateinit var binding: FragmentAddProject04Binding
    private val viewModel: AddProjectViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getInverterEfficiency().value?.let {
            binding.inputInverterEfficiency.setText("$it")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject04Binding.bind(view)
        verifyInputs()
        backFragment()
    }

    private fun verifyInputs() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            when {
                binding.inputInverterEfficiency.text.isNullOrEmpty() -> binding.inputInverterEfficiency.error =
                    getString(R.string.required)
                binding.inputInverterEfficiency.text.toString().toDouble() < 90.0 ||
                        binding.inputInverterEfficiency.text.toString()
                            .toDouble() > 99.5 -> binding.inputInverterEfficiency.error =
                    getString(R.string.out_of_range)
                else -> nextFragment()
            }
        }
    }

    private fun nextFragment() {
        val inverterEfficiency = binding.inputInverterEfficiency.text.toString().toDouble()
        viewModel.setInverterEfficiency(inverterEfficiency)
        viewModel.getTypeProject().value?.let {
            when (it) {
                0 -> findNavController().navigate(R.id.action_addProjectFragment04_to_addProjectFragment06)
                else -> findNavController().navigate(R.id.action_addProjectFragment04_to_addProjectFragment05)
            }
        }
    }

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}