package ru.spiridonov.smartservermobile.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.SmartServerApp
import ru.spiridonov.smartservermobile.databinding.FragmentHomeBinding
import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.entity.Security
import ru.spiridonov.smartservermobile.presentation.MainActivity
import ru.spiridonov.smartservermobile.presentation.MainActivityState
import ru.spiridonov.smartservermobile.presentation.MainViewModel
import ru.spiridonov.smartservermobile.presentation.ViewModelFactory
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val component by lazy {
        (requireActivity().application as SmartServerApp).component
    }

    private var tempSetJob: Job? = null
    private lateinit var raspState: RaspState
    private lateinit var securityState: Security

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel
    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        mainViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
        observeMainViewModel()
        observeHomeViewModel()
        btnClickHandler()
    }

    private fun btnClickHandler() {
        binding.btnPlusTemp.setOnClickListener {
            val temp = (binding.etRequiredTemp.text.toString()).toInt() + 1
            binding.etRequiredTemp.setText(temp.toString())
            setNewRequiredTemp()

        }
        binding.btnMinusTemp.setOnClickListener {
            val temp = (binding.etRequiredTemp.text.toString()).toInt() - 1
            binding.etRequiredTemp.setText(temp.toString())
            setNewRequiredTemp()
        }
        binding.etRequiredTemp.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    setNewRequiredTemp()
                    return true
                }
                return false
            }
        })
        binding.setSecurity.setOnClickListener {
            (requireActivity() as MainActivity).changeProgressBarState()
            viewModel.setNewSecurity(!securityState.isSecurityTurnOn)
        }
    }

    private fun setNewRequiredTemp() {
        tempSetJob?.cancel()
        tempSetJob = lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            viewModel.setNewRequiredState(newTemp = binding.etRequiredTemp.text.toString(), raspState = raspState)
        }
    }

    private fun observeHomeViewModel() {
        viewModel.requiredTemp.observe(viewLifecycleOwner) {
            binding.etRequiredTemp.setText(it.toString())
        }
    }

    private fun observeMainViewModel() {
        mainViewModel.mainActivityState.observe(viewLifecycleOwner) { mainActivityState ->
            when (mainActivityState) {
                is MainActivityState.NeedToReLogin -> {
                    (requireActivity() as MainActivity).changeProgressBarState(false)
                }

                is MainActivityState.SetupView -> {
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.RESUMED) {
                            mainViewModel.raspStateFlow
                                .collect { homeState ->
                                    when (homeState) {
                                        is HomeState.Loading -> {
                                            (requireActivity() as MainActivity).changeProgressBarState()

                                        }

                                        is HomeState.Content -> {
                                            viewModel.getRequiredTemp()
                                            binding.raspState = homeState.raspState
                                            raspState = homeState.raspState
                                            (requireActivity() as MainActivity).changeProgressBarState(false)
                                        }
                                    }
                                }
                        }
                    }

                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.RESUMED) {
                            mainViewModel.securityStateFlow
                                .collect { security ->
                                    binding.securityState = security
                                    securityState = security
                                    (requireActivity() as MainActivity).changeProgressBarState(false)
                                }
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}