package ru.spiridonov.smartservermobile.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetRequiredTempUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getRequiredTempUseCase: GetRequiredTempUseCase
): ViewModel() {

    fun getRequiredTemp() {
        viewModelScope.launch(Dispatchers.IO) {
            getRequiredTempUseCase.invoke().let {
                Log.d("HomeViewModel", "init: $it")
            }
        }
    }
}