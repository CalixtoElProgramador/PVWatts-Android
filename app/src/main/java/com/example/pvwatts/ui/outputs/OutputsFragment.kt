package com.example.pvwatts.ui.outputs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentOutputsBinding

class OutputsFragment : Fragment(R.layout.fragment_outputs) {

    private lateinit var binding: FragmentOutputsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOutputsBinding.bind(view)
    }

}