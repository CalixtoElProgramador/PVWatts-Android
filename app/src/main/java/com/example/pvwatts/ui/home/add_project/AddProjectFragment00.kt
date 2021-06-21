package com.example.pvwatts.ui.home.add_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentAddProject00Binding
import com.google.android.material.button.MaterialButton

class AddProjectFragment00 : Fragment(R.layout.fragment_add_project_00) {

    private lateinit var binding: FragmentAddProject00Binding
    private var optionSelected: Int? = null
    private val viewModel: AddProjectViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject00Binding.bind(view)

        nextFragment()
        backFragment()
        typeOfProjectSelected()

    }

    private fun nextFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            optionSelected?.let {
                viewModel.setTypeProject(it)
                findNavController().navigate(R.id.action_addProjectFragment00_to_addProjectFragment01)
                return@setOnClickListener
            }
            Toast.makeText(requireContext(), getString(R.string.select_an_option_first), Toast.LENGTH_SHORT).show()
        }
    }

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun typeOfProjectSelected() {
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rButton_gridConnected -> optionSelected = 0
                R.id.rButton_isolated -> optionSelected = 1
                R.id.rButton_Hybrid -> optionSelected = 2
            }
        }

    }

}