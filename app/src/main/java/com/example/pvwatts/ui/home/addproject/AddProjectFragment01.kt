package com.example.pvwatts.ui.home.addproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentAddProject01Binding
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_add_project_01.*

class AddProjectFragment01 : Fragment(R.layout.fragment_add_project_01) {

    private lateinit var binding: FragmentAddProject01Binding
    private val viewModel: AddProjectViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getProjectDemand().value?.let {
            binding.inputLoad.setText("${it.load}")
            binding.inputLoadMax.setText("${it.loadMax}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject01Binding.bind(view)
        verifyInputs()
        backFragment()
    }

    private fun verifyInputs() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            when {
                binding.inputLoad.text.isNullOrEmpty() -> inputLoad.error =
                    getString(R.string.required)
                binding.inputLoadMax.text.isNullOrEmpty() -> inputLoadMax.error =
                    getString(R.string.required)
                else -> nextFragment()
            }
        }
    }

    private fun nextFragment() {
        val load = binding.inputLoad.text.toString().toDouble()
        val loadMax = binding.inputLoadMax.text.toString().toDouble()
        viewModel.setProjectDemand(ProjectDemand(load, loadMax))
        findNavController().navigate(R.id.action_addProjectFragment01_to_addProjectFragment02)
    }

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}