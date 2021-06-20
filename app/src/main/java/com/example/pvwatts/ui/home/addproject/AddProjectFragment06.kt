package com.example.pvwatts.ui.home.addproject

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.navigation.fragment.findNavController
import com.example.pvwatts.R
import com.example.pvwatts.core.Result
import com.example.pvwatts.data.model.member.Project
import com.example.pvwatts.data.remote.home.HomeDataSource
import com.example.pvwatts.databinding.FragmentAddProject06Binding
import com.example.pvwatts.presentation.home.ProjectViewModel
import com.example.pvwatts.presentation.home.ProjectViewModelFactory
import com.example.pvwatts.repository.home.HomeRepoImpl
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*


class AddProjectFragment06 : Fragment(R.layout.fragment_add_project_06) {

    private lateinit var binding: FragmentAddProject06Binding
    private val viewModel: AddProjectViewModel by activityViewModels()
    private val REQUEST_IMAGE_CAPTURE = 1
    private var bitmap: Bitmap? = null
    private val viewModelFirebase by viewModels<ProjectViewModel> {
        ProjectViewModelFactory(HomeRepoImpl(HomeDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProject06Binding.bind(view)
        verifyInputs()
        takePicture()
        backFragment()
    }

    private fun takePicture() {
        binding.textSelectImage.setOnClickListener {
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

    private fun verifyInputs() {
        activity?.findViewById<MaterialButton>(R.id.button_next)?.setOnClickListener {
            when {
                binding.inputName.text.isNullOrEmpty() -> binding.inputName.error =
                    getString(R.string.required)
                binding.inputLocation.text.isNullOrEmpty() -> binding.inputLocation.error =
                    getString(R.string.required)
                bitmap == null -> Toast.makeText(
                    requireContext(),
                    getString(R.string.select_a_photo),
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    setArgsViewModel()
                }
            }
        }
    }

    private fun setArgsViewModel() {
        val name = binding.inputName.text.toString()
        val location = binding.inputLocation.text.toString()
        viewModel.setProjectDescription(ProjectDescription(name, location, bitmap!!))
        getAllParameters()
    }

    private fun getAllParameters() {
        val createdAt = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(Date())
        viewModel.getTypeProject().value?.let { typeProject ->
            when (typeProject) {
                0 -> {
                    val project = Project(
                        0.0,
                        0.0,
                        0.0,
                        createdAt,
                        0,
                        "",
                        viewModel.getInverterEfficiency().value!!,
                        viewModel.getProjectDemand().value!!.loadMax,
                        viewModel.getProjectDemand().value!!.load,
                        viewModel.getProjectDescription().value!!.location,
                        viewModel.getModuleSpecifications().value!!.moduleCurrent,
                        viewModel.getModuleSpecifications().value!!.modulePower,
                        viewModel.getModuleSpecifications().value!!.moduleVoltage,
                        viewModel.getProjectDescription().value!!.name,
                        viewModel.getWeatherConditions().value!!.sunHours,
                        viewModel.getWeatherConditions().value!!.temperature,
                        typeProject,
                        ""
                    )
                    createProjectFirebase(project)
                }
                else -> {
                    val project = Project(
                        viewModel.getBatterySpecifications().value!!.batteryCapacity,
                        viewModel.getBatterySpecifications().value!!.depthDischarge,
                        viewModel.getBatterySpecifications().value!!.batteryVoltage,
                        createdAt,
                        viewModel.getBatterySpecifications().value!!.daysAutonomy,
                        "",
                        viewModel.getInverterEfficiency().value!!,
                        viewModel.getProjectDemand().value!!.loadMax,
                        viewModel.getProjectDemand().value!!.load,
                        viewModel.getProjectDescription().value!!.location,
                        viewModel.getModuleSpecifications().value!!.moduleCurrent,
                        viewModel.getModuleSpecifications().value!!.modulePower,
                        viewModel.getModuleSpecifications().value!!.moduleVoltage,
                        viewModel.getProjectDescription().value!!.name,
                        viewModel.getWeatherConditions().value!!.sunHours,
                        viewModel.getWeatherConditions().value!!.temperature,
                        typeProject,
                        ""
                    )
                    createProjectFirebase(project)
                }
            }
        }
    }

    private fun createProjectFirebase(project: Project) {
        viewModelFirebase.setProject(bitmap!!, project)
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        isEnabledViews(false)
                    }
                    is Result.Success -> {
                        findNavController().navigate(R.id.action_addProjectFragment06_to_homeFragment2)
                        isEnabledViews(true)
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
        binding.inputLayoutLocation.isEnabled = boolean
        binding.textSelectImage.isEnabled = boolean
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

    private fun backFragment() {
        activity?.findViewById<MaterialButton>(R.id.button_back)?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.cardImageProject.visibility = View.VISIBLE
            binding.imageProject.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }

}