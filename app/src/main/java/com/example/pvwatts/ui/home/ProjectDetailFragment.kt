package com.example.pvwatts.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentProjectDetailBinding

class ProjectDetailFragment : Fragment(R.layout.fragment_project_detail) {

    private lateinit var binding: FragmentProjectDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProjectDetailBinding.bind(view)
    }

}