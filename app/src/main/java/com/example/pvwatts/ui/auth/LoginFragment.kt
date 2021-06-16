package com.example.pvwatts.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.core.Resource
import com.example.pvwatts.data.remote.auth.UserDataSource
import com.example.pvwatts.databinding.FragmentLoginBinding
import com.example.pvwatts.presentation.auth.AuthViewModel
import com.example.pvwatts.presentation.auth.AuthViewModelFactory
import com.example.pvwatts.repository.auth.AuthRepoImpl
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImpl(UserDataSource()))
    }
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        setViewErrorFalseAfterChanges()
        isUserLoggedIn()

        binding.buttonSignIn.setOnClickListener {
            validateInputs()
        }

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment00)
        }

    }

    private fun setViewErrorFalseAfterChanges() {
        binding.inputEmail.doAfterTextChanged {
            binding.textError.visibility = View.INVISIBLE
        }

        binding.inputPassword.doAfterTextChanged {
            binding.textError.visibility = View.INVISIBLE
            binding.inputLayoutPassword.isErrorEnabled = false
        }
    }

    private fun validateInputs() {
        val patternEmail = Patterns.EMAIL_ADDRESS.toRegex()

        when {
            binding.inputEmail.text.isNullOrEmpty() -> binding.inputEmail.error =
                getString(R.string.required)
            !binding.inputEmail.text.toString()
                .matches(patternEmail) -> {
                binding.textError.visibility = View.VISIBLE
                binding.textError.text = getString(R.string.insert_a_valid_email)
            }
            binding.inputPassword.text.isNullOrEmpty() -> binding.inputLayoutPassword.error =
                getString(R.string.required)
            binding.inputPassword.text.toString().length < 8 ->
                binding.inputLayoutPassword.error = getString(R.string.short_password_error)
            else -> {
                sendArguments()
            }
        }
    }

    private fun sendArguments() {
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString()

        viewModel.signIn(email, password).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    isEnabledViews(false)
                }
                is Resource.Success -> {
                    isEnabledViews(true)
                    findNavController().navigate(R.id.action_loginFragment_to_PVWattsActivity)
                    requireActivity().finish()

                }
                is Resource.Failure -> {
                    isEnabledViews(true)
                    binding.textError.visibility = View.VISIBLE
                    binding.textError.text = getString(R.string.error_login_verify_fields)

                    /*Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()*/
                }
            }
        })

    }

    @SuppressLint("ResourceAsColor")
    private fun isEnabledViews(boolean: Boolean) {
        binding.inputLayoutEmail.isEnabled = boolean
        binding.inputLayoutPassword.isEnabled = boolean
        binding.buttonSignIn.isEnabled = boolean
        binding.buttonSignUp.isEnabled = boolean
        binding.checkRemember.isEnabled = boolean
        binding.textRecoveryPassword.isEnabled = boolean

        if (boolean) {
            binding.buttonSignUp.setStrokeColorResource(R.color.purple_500)
            binding.buttonSignUp.setTextColor(R.color.purple_500)
            activity?.findViewById<FrameLayout>(R.id.progressBarLogin)?.visibility = View.GONE
        } else {
            binding.buttonSignUp.setStrokeColorResource(R.color.colorIcons)
            binding.buttonSignUp.setTextColor(R.color.colorIcons)
            activity?.findViewById<FrameLayout>(R.id.progressBarLogin)?.visibility = View.VISIBLE
        }

    }

    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_PVWattsActivity)
        }
    }

}