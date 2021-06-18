package com.example.pvwatts.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.ActivityAuthBinding
import com.example.pvwatts.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        createNewProject()

    }

    private fun createNewProject() {
        /*activity?.findViewById<ExtendedFloatingActionButton>(R.id.eFAB_AddProject)
            ?.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addProjectFragment00)
            }*/
    }

}