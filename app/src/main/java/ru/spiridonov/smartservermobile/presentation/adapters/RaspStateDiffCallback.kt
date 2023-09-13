package ru.spiridonov.smartservermobile.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.spiridonov.smartservermobile.domain.entity.RaspState

class RaspStateDiffCallback : DiffUtil.ItemCallback<RaspState>() {
    override fun areItemsTheSame(oldItem: RaspState, newItem: RaspState): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RaspState, newItem: RaspState): Boolean =
        oldItem == newItem
}