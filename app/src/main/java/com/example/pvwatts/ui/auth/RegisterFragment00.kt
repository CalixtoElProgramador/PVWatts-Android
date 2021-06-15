package com.example.pvwatts.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentRegister00Binding
import com.google.android.material.button.MaterialButton

class RegisterFragment00 : Fragment(R.layout.fragment_register_00) {

    private lateinit var binding: FragmentRegister00Binding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegister00Binding.bind(view)

        getArgsViewModel()
        setViewErrorFalseAfterChanges()

        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            validateInputs()
        }

        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun validateInputs() {
        when {
            binding.inputName.text.isNullOrEmpty() -> binding.inputLayoutName.error =
                getString(R.string.required)
            binding.inputLastName.text.isNullOrEmpty() -> binding.inputLayoutLastName.error =
                getString(R.string.required)
            binding.inputEmail.text.isNullOrEmpty() -> binding.inputLayoutEmail.error =
                getString(R.string.required)
            else -> {
                sendArguments()
            }
        }
    }

    private fun sendArguments() {
        val name = binding.inputName.text.toString().trim()
        val lastname = binding.inputLastName.text.toString().trim()
        val email = binding.inputEmail.text.toString().trim()

        viewModel.setPerson(Person(name, lastname, email))

        findNavController().navigate(R.id.action_registerFragment00_to_registerFragment01)
    }

    private fun setViewErrorFalseAfterChanges() {
        binding.inputName.doAfterTextChanged {
            binding.inputLayoutName.isErrorEnabled = false
        }
        binding.inputLastName.doAfterTextChanged {
            binding.inputLayoutLastName.isErrorEnabled = false
        }
        binding.inputEmail.doAfterTextChanged {
            binding.inputLayoutEmail.isErrorEnabled = false
        }
    }

    private fun getArgsViewModel() {
        viewModel.getPerson().value?.let {
            binding.inputName.setText(it.name)
            binding.inputLastName.setText(it.lastname)
            binding.inputEmail.setText(it.email)
        }
    }

}