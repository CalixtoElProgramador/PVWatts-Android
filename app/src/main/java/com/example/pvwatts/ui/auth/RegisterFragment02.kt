package com.example.pvwatts.ui.auth

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.core.Resource
import com.example.pvwatts.data.remote.auth.UserDataSource
import com.example.pvwatts.databinding.FragmentRegister02Binding
import com.example.pvwatts.presentation.auth.AuthViewModel
import com.example.pvwatts.presentation.auth.AuthViewModelFactory
import com.example.pvwatts.repository.auth.AuthRepoImpl
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment02 : Fragment(R.layout.fragment_register_02) {

    private lateinit var binding: FragmentRegister02Binding
    private val REQUEST_IMAGE_CAPTURE = 1
    private var bitmap: Bitmap? = null
    private val viewModel: RegisterViewModel by activityViewModels()
    private val viewModelFirebase by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImpl(UserDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegister02Binding.bind(view)

        viewModel.getBitmap().let {
            binding.profilePicture.setImageBitmap(it.value)
            bitmap = it.value
        }

        setProfilePicture()

        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            bitmap?.let { image ->
                viewModel.setBitmap(image)

                viewModel.getPerson().value?.let { person ->
                    viewModel.getPassword().value?.let { password ->
                        viewModel.getBitmap().value?.let { bitmap ->
                            viewModelFirebase.signUp(
                                person.name,
                                person.lastname,
                                person.email,
                                password.passwrod,
                                bitmap
                            ).observe(viewLifecycleOwner, { result ->
                                when (result) {
                                    is Resource.Loading -> {
                                        isEnabledViews(false)
                                    }
                                    is Resource.Success -> {
                                        findNavController().navigate(R.id.action_registerFragment02_to_PVWattsActivity)
                                        isEnabledViews(true)
                                        requireActivity().finish()
                                    }
                                    is Resource.Failure -> {
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
                    }
                }
                return@setOnClickListener
            }
            Toast.makeText(requireContext(), getString(R.string.select_a_photo), Toast.LENGTH_SHORT)
                .show()
        }

        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            bitmap?.let { image ->
                viewModel.setBitmap(image)
            }
            requireActivity().onBackPressed()
        }

    }

    private fun setProfilePicture() {
        binding.profilePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.there_is_no_app_to_take_a_photo),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun isEnabledViews(boolean: Boolean) {
        binding.profilePicture.isEnabled = boolean
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profilePicture.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
            bitmap?.let {
                viewModel.setBitmap(bitmap!!)
            }
        }
    }

}