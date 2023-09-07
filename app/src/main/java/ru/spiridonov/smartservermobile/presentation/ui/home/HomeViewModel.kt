package ru.spiridonov.smartservermobile.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetRequiredTempUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getRequiredTempUseCase: GetRequiredTempUseCase
) : ViewModel() {

    private val _requiredTemp = MutableLiveData<Int>()
    val requiredTemp: LiveData<Int>
        get() = _requiredTemp

    fun getRequiredTemp() {
        viewModelScope.launch(Dispatchers.IO) {
            getRequiredTempUseCase.invoke().let {
                _requiredTemp.postValue(it)
            }
        }
    }
}