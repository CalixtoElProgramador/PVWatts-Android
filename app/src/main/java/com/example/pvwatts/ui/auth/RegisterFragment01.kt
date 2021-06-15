package com.example.pvwatts.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentRegister01Binding
import com.google.android.material.button.MaterialButton

class RegisterFragment01 : Fragment(R.layout.fragment_register_01) {

    private lateinit var binding: FragmentRegister01Binding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getPassword().value?.let {
            binding.inputPassword.setText(it.passwrod)
            binding.inputPasswordConfirm.setText(it.passwrodConfirm)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegister01Binding.bind(view)

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
            binding.inputPassword.text.isNullOrEmpty() -> binding.inputLayoutPassword.error =
                getString(R.string.required)
            binding.inputPasswordConfirm.text.isNullOrEmpty() -> binding.inputLayoutPasswordConfirm.error =
                getString(R.string.required)
            binding.inputPassword.text.toString().trim() != binding.inputPasswordConfirm.text.toString().trim() ->
                binding.inputLayoutPasswordConfirm.error = getString(R.string.passwords_are_not_the_same)
            else -> {
                sendArguments()
            }
        }
    }

    private fun sendArguments() {
        val password = binding.inputPassword.text.toString()
        val passwordConfirm = binding.inputPasswordConfirm.text.toString()

        viewModel.setPassword(Password(password, passwordConfirm))

        findNavController().navigate(R.id.action_registerFragment01_to_registerFragment02)
    }

    private fun setViewErrorFalseAfterChanges() {
        binding.inputPassword.doAfterTextChanged {
            binding.inputLayoutPassword.isErrorEnabled = false
        }
        binding.inputPasswordConfirm.doAfterTextChanged {
            binding.inputLayoutPasswordConfirm.isErrorEnabled = false
        }
    }

}