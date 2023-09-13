package ru.spiridonov.smartservermobile.presentation.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.spiridonov.smartservermobile.SmartServerApp
import ru.spiridonov.smartservermobile.databinding.FragmentNotificationsBinding
import ru.spiridonov.smartservermobile.presentation.ViewModelFactory
import ru.spiridonov.smartservermobile.presentation.adapters.RaspStateAdapter
import javax.inject.Inject

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding: FragmentNotificationsBinding
        get() = _binding ?: throw RuntimeException("FragmentNotificationsBinding is null")

    private val component by lazy {
        (requireActivity().application as SmartServerApp).component
    }

    private val raspStateAdapter by lazy {
        RaspStateAdapter()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NotificationsViewModel
    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotificationsViewModel::class.java]
        observeViewModel()
        binding.rvRaspState.adapter = raspStateAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getViolatedSecurityList()
    }

    private fun observeViewModel(){
        viewModel.violatedSecurityList.observe(viewLifecycleOwner){
            raspStateAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}