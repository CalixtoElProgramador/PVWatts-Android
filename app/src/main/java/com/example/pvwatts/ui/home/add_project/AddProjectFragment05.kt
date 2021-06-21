package com.example.pvwatts.ui.home.add_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentAddProject05Binding
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_add_project_00.view.*

class AddProjectFragment05 : Fragment(R.layout.fragment_add_project_05) {

    private lateinit var binding: FragmentAddProject05Binding
    private val viewModel: AddProjectViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getBatterySpecifications().value?.let {
            binding.inputDaysAutonomy.setText("${it.daysAutonomy}")
            binding.inputDepthDischarge.setText("${it.depthDischarge}")
            binding.inputBatteryCapacity.setText("${it.batteryCapacity}")
            binding.inputBatteryVoltage.setText("${it.batteryVoltage}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject05Binding.bind(view)
        verifyInputs()
        backFragment()
    }

    private fun verifyInputs() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            when {
                binding.inputDaysAutonomy.text.isNullOrEmpty() -> binding.inputDaysAutonomy.error =
                    getString(R.string.required)
                binding.inputDepthDischarge.text.isNullOrEmpty() -> binding.inputDepthDischarge.error =
                    getString(R.string.required)
                binding.inputBatteryCapacity.text.isNullOrEmpty() -> binding.inputBatteryCapacity.error =
                    getString(R.string.required)
                binding.inputBatteryVoltage.text.isNullOrEmpty() -> binding.inputBatteryVoltage.error =
                    getString(R.string.required)
                else -> nextFragment()
            }
        }
    }

    private fun nextFragment() {
        val daysAutonomy = binding.inputDaysAutonomy.text.toString().toInt()
        val depthDischarge = binding.inputDepthDischarge.text.toString().toDouble()
        val batteryCapacity = binding.inputBatteryCapacity.text.toString().toDouble()
        val batteryVoltage = binding.inputBatteryVoltage.text.toString().toDouble()
        viewModel.setBatterySpecifications(
            BatterySpecifications(
                daysAutonomy,
                depthDischarge,
                batteryCapacity,
                batteryVoltage
            )
        )
        findNavController().navigate(R.id.action_addProjectFragment05_to_addProjectFragment06)
    }

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}