package com.example.pvwatts.ui.home.detail_project

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.pvwatts.R
import com.example.pvwatts.databinding.FragmentProjectDetailBinding
import kotlin.math.ceil

class ProjectDetailFragment : Fragment(R.layout.fragment_project_detail) {

    private lateinit var binding: FragmentProjectDetailBinding
    private val args by navArgs<ProjectDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProjectDetailBinding.bind(view)

        if (args.typeProject != 0) {
            binding.cardBatterySpecifications.visibility = View.VISIBLE
            binding.inputLayoutControllerOf.visibility = View.VISIBLE
            val controllerVoltage = when {
                (args.loadMonth / 30) * 1000 >= 0 && (args.loadMonth / 30) * 1000 < 2000 -> 12
                (args.loadMonth / 30) * 1000 >= 2001 && (args.loadMonth / 30) * 1000 < 4000 -> 24
                else -> 48
            }
            binding.inputControllerOf.setText("$controllerVoltage")

            binding.inputDaysAutonomy.setText("${args.daysAutonomy}")
            binding.inputBatteryDischarge.setText("${args.batteryDepthDischarge}")
            binding.inputBatteryCapacity.setText("${args.batteryCapacity}")
            binding.inputBatteryVoltage.setText("${args.batteryVoltage}")
            val currentPerHour = ((args.loadMonth / 30) / args.inverterEfficiency) / controllerVoltage
            val batteriesParallel =
                ceil((currentPerHour / args.batteryDepthDischarge) / args.batteryCapacity).toInt()
            val batteriesSeries = (controllerVoltage / args.batteryVoltage).toInt()
            val batteriesQuantity = batteriesParallel + batteriesSeries
            binding.inputBatteryQuantity.setText("$batteriesQuantity")
            binding.inputLayoutBatteryQuantity.helperText =
                "Where $batteriesSeries batteries are in series and $batteriesParallel in parallel"

        } else {
            binding.cardBatterySpecifications.visibility = View.GONE
            binding.inputLayoutControllerOf.visibility = View.GONE
        }

        binding.inputLoadMonth.setText("${args.loadMonth}")
        binding.inputLoadDaily.setText("${args.loadMonth / 30}")

        binding.inputModulePower.setText("${args.modulePower}")
        binding.inputModuleVoltage.setText("${args.moduleVoltage}")
        binding.inputModuleCurrent.setText("${args.moduleCurrent}")
        val systemSize = (args.loadMonth / 30) / args.sunHours
        val modulesQuantity = (systemSize / args.modulePower) * 1000
        val modulesNeeded = ceil(modulesQuantity * 1.25).toInt()
        binding.inputModuleQuantity.setText("$modulesNeeded")
        binding.inputSystemSize.setText("$systemSize")

        binding.inputInverterEfficiency.setText("${args.inverterEfficiency}")
        binding.inputInverterOf.setText("${ceil(systemSize)}")

        binding.inputTemperature.setText("${args.temperature}")
        binding.inputSunHours.setText("${args.sunHours}")
        binding.inputLocation.setText(args.location)


    }

}