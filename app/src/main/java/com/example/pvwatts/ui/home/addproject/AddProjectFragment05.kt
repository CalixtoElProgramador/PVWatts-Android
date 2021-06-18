package com.example.pvwatts.ui.home.addproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentAddProject05Binding
import com.google.android.material.button.MaterialButton

class AddProjectFragment05 : Fragment(R.layout.fragment_add_project_05) {

    private lateinit var binding: FragmentAddProject05Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject05Binding.bind(view)
        nextFragment()
        backFragment()
    }

    private fun nextFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            findNavController().navigate(R.id.action_addProjectFragment05_to_addProjectFragment06)
        }
    }

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}