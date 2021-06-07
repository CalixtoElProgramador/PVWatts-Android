package com.example.pvwatts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pvwatts.databinding.FragmentOutputsBinding

class OutputsFragment : Fragment(R.layout.fragment_outputs) {

    private lateinit var binding: FragmentOutputsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOutputsBinding.bind(view)
    }

}