package ru.spiridonov.smartservermobile.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.SmartServerApp
import ru.spiridonov.smartservermobile.databinding.FragmentHomeBinding
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
        observeViewModels()
    }

    private fun observeViewModels() {
        mainViewModel.mainActivityState.observe(viewLifecycleOwner) { mainActivityState ->
            when (mainActivityState) {
                is MainActivityState.NeedToReLogin -> {
                    binding.textHome.text = "Необходимо войти в аккаунт"
                    binding.pbLoading.visibility = View.GONE
                }

                is MainActivityState.SetupView -> {
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.RESUMED) {
                            mainViewModel.raspStateFlow
                                .collect { homeState ->
                                    when (homeState) {
                                        is HomeState.Loading -> {
                                            Log.d("HomeFragment", "observeViewModels: Loading")
                                            binding.pbLoading.visibility = View.VISIBLE
                                        }

                                        is HomeState.Content -> {
                                            viewModel.getRequiredTemp()
                                            Log.d(
                                                "HomeFragment",
                                                "observeViewModels: ${homeState.raspState}"
                                            )
                                            binding.textHome.text = homeState.raspState.toString()
                                            binding.pbLoading.visibility = View.GONE
                                        }
                                    }
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