package com.example.pvwatts.ui.home.add_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentAddProject02Binding
import com.google.android.material.button.MaterialButton

class AddProjectFragment02 : Fragment(R.layout.fragment_add_project_02) {

    private lateinit var binding: FragmentAddProject02Binding
    private val viewModel: AddProjectViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getWeatherConditions().value?.let {
            binding.inputSunHours.setText("${it.sunHours}")
            binding.inputTemperature.setText("${it.temperature}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject02Binding.bind(view)
        verifyInputs()
        backFragment()
    }

    private fun verifyInputs() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            when {
                binding.inputSunHours.text.isNullOrEmpty() -> binding.inputSunHours.error =
                    getString(R.string.required)
                binding.inputSunHours.text.toString()
                    .toInt() == 0 || binding.inputSunHours.text.toString().toInt() > 23 ->
                    binding.inputSunHours.error = getString(R.string.out_of_range)
                binding.inputTemperature.text.isNullOrEmpty() -> binding.inputTemperature.error =
                    getString(R.string.required)
                else -> nextFragment()
            }
        }
    }

    private fun nextFragment() {
        setArgsViewModel()
        findNavController().navigate(R.id.action_addProjectFragment02_to_addProjectFragment03)
    }

    private fun setArgsViewModel() {
        val sunHours = binding.inputSunHours.text.toString().toInt()
        val temperature = binding.inputTemperature.text.toString().toInt()
        viewModel.setWeatherConditions(WeatherConditions(sunHours, temperature))
    }

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}