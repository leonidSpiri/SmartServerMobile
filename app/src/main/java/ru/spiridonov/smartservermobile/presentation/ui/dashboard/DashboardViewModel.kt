package ru.spiridonov.smartservermobile.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetRaspStateListByDateUseCase
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getRaspStateListByDateUseCase: GetRaspStateListByDateUseCase
) : ViewModel() {

    private val _raspStateList = MutableLiveData<List<RaspState>>()
    val raspStateList: LiveData<List<RaspState>>
        get() = _raspStateList

    fun getRaspStateListByDate(date: String) =
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
            _raspStateList.postValue(getRaspStateListByDateUseCase.invoke(date))
        }
}