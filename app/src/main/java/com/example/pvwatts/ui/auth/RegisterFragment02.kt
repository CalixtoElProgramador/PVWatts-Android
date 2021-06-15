package com.example.pvwatts.ui.auth

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentRegister02Binding
import com.google.android.material.button.MaterialButton

class RegisterFragment02 : Fragment(R.layout.fragment_register_02) {

    private lateinit var binding: FragmentRegister02Binding
    private val REQUEST_IMAGE_CAPTURE = 1
    private var bitmap: Bitmap? = null
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegister02Binding.bind(view)

        binding.profilePicture.setOnClickListener {
            val takePictureIntet = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntet, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.there_is_no_app_to_take_a_photo),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            bitmap?.let {
                viewModel.setBitmap(it)
                viewModel.getPerson().value?.let { person ->
                    viewModel.getPassword().value?.let { password ->
                        viewModel.getBitmap().value?.let { bitmap ->
                            findNavController().navigate(R.id.action_registerFragment02_to_PVWattsActivity)
                            requireActivity().finish()
                            return@setOnClickListener
                        }
                    }
                }
            }
            Toast.makeText(requireContext(), getString(R.string.select_a_photo), Toast.LENGTH_SHORT).show()
        }

        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profilePicture.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }

}