package com.example.pvwatts.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.core.Result
import com.example.pvwatts.data.remote.auth.UserDataSource
import com.example.pvwatts.databinding.FragmentRegister00Binding
import com.example.pvwatts.presentation.auth.AuthViewModel
import com.example.pvwatts.presentation.auth.AuthViewModelFactory
import com.example.pvwatts.repository.auth.AuthRepoImpl
import com.google.android.material.button.MaterialButton

class RegisterFragment00 : Fragment(R.layout.fragment_register_00) {

    private lateinit var binding: FragmentRegister00Binding
    private val viewModel: RegisterViewModel by activityViewModels()
    private val viewModelFireBase by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImpl(UserDataSource()))
    }

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
        val patternEmail = Patterns.EMAIL_ADDRESS.toRegex()

        when {
            binding.inputName.text.isNullOrEmpty() -> binding.inputLayoutName.error =
                getString(R.string.required)
            binding.inputLastName.text.isNullOrEmpty() -> binding.inputLayoutLastName.error =
                getString(R.string.required)
            binding.inputEmail.text.isNullOrEmpty() -> binding.inputLayoutEmail.error =
                getString(R.string.required)
            !binding.inputEmail.text.toString()
                .matches(patternEmail) -> binding.inputLayoutEmail.error =
                getString(R.string.insert_a_valid_email)
            else -> {
                sendArguments()
            }
        }
    }

    private fun sendArguments() {
        val name = binding.inputName.text.toString().trim()
        val lastname = binding.inputLastName.text.toString().trim()
        val email = binding.inputEmail.text.toString().trim()

        // Check if the entered email is already logged in to the FirabaseFireStore.
        viewModelFireBase.isEmailRegister(email).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    isEnabledViews(false)
                }
                is Result.Success -> {
                    isEnabledViews(true)
                    if (!result.data) {
                        binding.inputLayoutEmail.error =
                            getString(R.string.this_email_is_already_in_use)
                    } else {
                        viewModel.setPerson(Person(name, lastname, email))
                        findNavController().navigate(R.id.action_registerFragment00_to_registerFragment01)
                    }
                }
                is Result.Failure -> {
                    isEnabledViews(true)
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun isEnabledViews(boolean: Boolean) {
        binding.inputLayoutName.isEnabled = boolean
        binding.inputLayoutLastName.isEnabled = boolean
        binding.inputLayoutEmail.isEnabled = boolean
        activity?.findViewById<MaterialButton>(R.id.button_back)?.isEnabled = boolean
        if (boolean) {
            activity?.findViewById<MaterialButton>(R.id.button_back)
                ?.setStrokeColorResource(R.color.purple_500)
            activity?.findViewById<MaterialButton>(R.id.button_back)
                ?.setTextColor(R.color.purple_500)

            activity?.findViewById<MaterialButton>(R.id.button_next)?.visibility = View.VISIBLE
            activity?.findViewById<FrameLayout>(R.id.progressBar)?.visibility = View.GONE

        } else {
            activity?.findViewById<MaterialButton>(R.id.button_back)
                ?.setStrokeColorResource(R.color.colorIcons)
            activity?.findViewById<MaterialButton>(R.id.button_back)
                ?.setTextColor(R.color.colorIcons)
            activity?.findViewById<MaterialButton>(R.id.button_next)?.visibility = View.GONE
            activity?.findViewById<FrameLayout>(R.id.progressBar)?.visibility = View.VISIBLE
        }
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