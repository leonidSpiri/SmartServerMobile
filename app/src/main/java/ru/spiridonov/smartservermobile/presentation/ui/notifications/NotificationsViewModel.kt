package ru.spiridonov.smartservermobile.presentation.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetViolatedSecurityListUseCase
import javax.inject.Inject

class NotificationsViewModel @Inject constructor(
    private val getViolatedSecurityListUseCase: GetViolatedSecurityListUseCase
) : ViewModel() {

    private val _violatedSecurityList = MutableLiveData<List<RaspState>>()
    val violatedSecurityList: LiveData<List<RaspState>>
        get() = _violatedSecurityList

    fun getViolatedSecurityList() {
        viewModelScope.launch(Dispatchers.IO) {
            _violatedSecurityList.postValue(getViolatedSecurityListUseCase.invoke())
        }
    }

}