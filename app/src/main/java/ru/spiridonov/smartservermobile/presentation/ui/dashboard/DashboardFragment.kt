package ru.spiridonov.smartservermobile.presentation.ui.dashboard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import ru.spiridonov.smartservermobile.R
import ru.spiridonov.smartservermobile.SmartServerApp
import ru.spiridonov.smartservermobile.databinding.FragmentDashboardBinding
import ru.spiridonov.smartservermobile.domain.entity.DevTypes
import ru.spiridonov.smartservermobile.presentation.MainActivity
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
        observeViewModel()
        buttonClickListener()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).changeProgressBarState()
        viewModel.getRaspStateListByDate(viewModel.todayDate())
        setDefaultButtonsState()
        binding.btnToday.strokeColor = ColorStateList.valueOf(Color.RED)
        binding.btnToday.setTextColor(Color.RED)
    }

    private fun buttonClickListener() {
        binding.btnToday.setOnClickListener {
            viewModel.getRaspStateListByDate(viewModel.todayDate())
            setDefaultButtonsState()
            binding.btnToday.strokeColor = ColorStateList.valueOf(Color.RED)
            binding.btnToday.setTextColor(Color.RED)
            (requireActivity() as MainActivity).changeProgressBarState()
        }
        binding.btnYesterday.setOnClickListener {
            viewModel.getRaspStateListByDate(viewModel.yesterdayDate())
            setDefaultButtonsState()
            binding.btnYesterday.strokeColor = ColorStateList.valueOf(Color.RED)
            binding.btnYesterday.setTextColor(Color.RED)
            (requireActivity() as MainActivity).changeProgressBarState()
        }
        binding.btnTwoYesterday.setOnClickListener {
            viewModel.getRaspStateListByDate(viewModel.twoDaysAgoDate())
            setDefaultButtonsState()
            binding.btnTwoYesterday.strokeColor = ColorStateList.valueOf(Color.RED)
            binding.btnTwoYesterday.setTextColor(Color.RED)
            (requireActivity() as MainActivity).changeProgressBarState()
        }
    }

    private fun observeViewModel() {
        viewModel.raspStateList.observe(viewLifecycleOwner) { raspStateList ->
            (requireActivity() as MainActivity).changeProgressBarState(false)
            if (raspStateList.isEmpty())
                Toast.makeText(
                    requireContext(),
                    "Нет данных за выбранный день",
                    Toast.LENGTH_SHORT
                ).show()
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

    private fun setDefaultButtonsState() =
        ColorStateList.valueOf(resources.getColor(R.color.blue, requireContext().theme)).apply {
            binding.btnToday.strokeColor = this
            binding.btnToday.setTextColor(this)
            binding.btnYesterday.strokeColor = this
            binding.btnYesterday.setTextColor(this)
            binding.btnTwoYesterday.strokeColor = this
            binding.btnTwoYesterday.setTextColor(this)
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}