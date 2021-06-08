package com.example.pvwatts.ui.inputs

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentInputsBinding

class InputsFragment : Fragment(R.layout.fragment_inputs) {

    private lateinit var binding: FragmentInputsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputsBinding.bind(view)

        binding.buttonExpandCardView.setOnClickListener {
            if (binding.constraintLayoutCardExpandable.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardAdvancedParameters, AutoTransition())
                binding.constraintLayoutCardExpandable.visibility = View.VISIBLE
                binding.buttonExpandCardView.setBackgroundResource(R.drawable.ic_arrow_up)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardAdvancedParameters, AutoTransition())
                binding.constraintLayoutCardExpandable.visibility = View.GONE
                binding.buttonExpandCardView.setBackgroundResource(R.drawable.ic_arrow_down)
            }
        }

        binding.buttonCalculate.setOnClickListener {
            Toast.makeText(activity, "Calculating...", Toast.LENGTH_SHORT).show()
        }

    }

}