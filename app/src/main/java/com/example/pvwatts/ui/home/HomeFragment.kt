package com.example.pvwatts.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.example.pvwatts.R
import com.example.pvwatts.core.Result
import com.example.pvwatts.data.model.member.Project
import com.example.pvwatts.data.remote.home.HomeDataSource
import com.example.pvwatts.databinding.FragmentHomeBinding
import com.example.pvwatts.presentation.home.ProjectViewModel
import com.example.pvwatts.presentation.home.ProjectViewModelFactory
import com.example.pvwatts.repository.home.HomeRepoImpl
import com.example.pvwatts.ui.home.adapters.ProjectAdapter
import kotlin.math.abs

class HomeFragment : Fragment(R.layout.fragment_home), ProjectAdapter.OnProjectClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ProjectViewModel> {
        ProjectViewModelFactory(HomeRepoImpl(HomeDataSource()))
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        configurationViewPager()

        viewModel.getUser().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.homeFragment.visibility = View.GONE
                    activity?.findViewById<FrameLayout>(R.id.progressBarHome)?.visibility =
                        View.VISIBLE
                }
                is Result.Success -> {
                    activity?.findViewById<FrameLayout>(R.id.progressBarHome)?.visibility =
                        View.GONE
                    binding.homeFragment.visibility = View.VISIBLE
                    binding.textHelloUser.text = "Hello, ${result.data?.name}"
                    Glide.with(requireContext()).load(result.data?.photo_url).centerCrop()
                        .into(binding.imageProfile)
                }
                is Result.Failure -> {
                }
            }
        })

        viewModel.getProject().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        binding.messageVpEmpty.visibility = View.VISIBLE
                        binding.viewPagerProjects.visibility = View.GONE
                        return@Observer
                    } else {
                        binding.messageVpEmpty.visibility = View.GONE
                        binding.viewPagerProjects.visibility = View.VISIBLE
                    }
                    binding.viewPagerProjects.adapter =
                        ProjectAdapter(result.data, this@HomeFragment)
                }
                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrió un error: ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun configurationViewPager() {
        binding.viewPagerProjects.clipToPadding = false
        binding.viewPagerProjects.clipChildren = false
        binding.viewPagerProjects.offscreenPageLimit = 3
        binding.viewPagerProjects.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.95f + r * 0.05f
        }

        binding.viewPagerProjects.setPageTransformer(compositePageTransformer)
    }

    override fun onProjectClick(project: Project) {
        val action = HomeFragmentDirections.actionHomeFragmentToProjectDetailFragment(
            project.battery_capacity.toFloat(),
            project.battery_depth_discharge.toFloat(),
            project.battery_voltage.toFloat(),
            project.created_at,
            project.days_autonomy,
            project.image_url,
            project.inverter_efficiency.toFloat(),
            project.load_max.toFloat(),
            project.load_month.toFloat(),
            project.module_power.toFloat(),
            project.module_voltage.toFloat(),
            project.name,
            project.sun_hours,
            project.temperature,
            project.type_project,
            project.uid,
            project.module_current.toFloat(),
            project.location
        )
        findNavController().navigate(action)
    }
}