package ru.spiridonov.smartservermobile.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import ru.spiridonov.smartservermobile.R
import ru.spiridonov.smartservermobile.databinding.ItemSecurityViolatedBinding
import ru.spiridonov.smartservermobile.domain.entity.RaspState

class RaspStateAdapter : ListAdapter<RaspState, RaspStateViewHolder>(RaspStateDiffCallback()) {

    var onItemClickListener: ((RaspState) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaspStateViewHolder {
        val layoutID =
            when (viewType) {
                SECURITY_VIOLATED -> R.layout.item_security_violated
                else -> throw RuntimeException("Unknown view type: $viewType")
            }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutID,
            parent,
            false
        )
        return RaspStateViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int =
        SECURITY_VIOLATED


    override fun onBindViewHolder(holder: RaspStateViewHolder, position: Int) {
        val raspState = getItem(position)
        val binding = holder.binding
        when (binding) {
            is ItemSecurityViolatedBinding -> {
                binding.raspState = raspState
            }


        }

        binding.root.setOnClickListener {
            onItemClickListener?.invoke(raspState)
        }

    }

    companion object {
        const val SECURITY_VIOLATED = 0
    }
}