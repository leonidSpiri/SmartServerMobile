package ru.spiridonov.smartservermobile.presentation.ui.dashboard

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import ru.spiridonov.smartservermobile.SmartServerApp
import ru.spiridonov.smartservermobile.databinding.FragmentDashboardBinding
import ru.spiridonov.smartservermobile.domain.entity.DevTypes
import ru.spiridonov.smartservermobile.presentation.ViewModelFactory
import javax.inject.Inject

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding
        get() = _binding ?: throw RuntimeException("FragmentDashboardBinding is null")

    private val component by lazy {
        (requireActivity().application as SmartServerApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: DashboardViewModel
    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]
        viewModel.getRaspStateListByDate("2023-08-15")
        viewModel.raspStateList.observe(viewLifecycleOwner) {raspStateList ->

            val chart = binding.cubiclinechartHumidity
            val series = ValueLineSeries()
            series.color = Color.parseColor("#52BF25")
            chart.clearChart()
            raspStateList.forEach { raspState ->
                raspState.raspState.find { state -> state.first.devType == DevTypes.TEMP_SENSOR }?.second?.toInt()
                    ?.let { temp ->
                        series.addPoint(
                            ValueLinePoint(
                                "${raspState.dateTime.hour}:${raspState.dateTime.minute}",
                                temp.toFloat()
                            )
                        )
                    }
            }
            chart.addSeries(series)
            chart.startAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}