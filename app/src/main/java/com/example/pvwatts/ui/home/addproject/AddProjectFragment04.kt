package com.example.pvwatts.ui.home.addproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentAddProject04Binding
import com.google.android.material.button.MaterialButton

class AddProjectFragment04 : Fragment(R.layout.fragment_add_project_04) {

    private lateinit var binding: FragmentAddProject04Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject04Binding.bind(view)
        nextFragment()
        backFragment()
    }

    private fun nextFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            findNavController().navigate(R.id.action_addProjectFragment04_to_addProjectFragment05)
        }
    }

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}