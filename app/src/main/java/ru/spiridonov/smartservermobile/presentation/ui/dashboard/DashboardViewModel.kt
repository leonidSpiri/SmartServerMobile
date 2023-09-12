package ru.spiridonov.smartservermobile.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetRaspStateListByDateUseCase
import java.time.OffsetDateTime
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getRaspStateListByDateUseCase: GetRaspStateListByDateUseCase
) : ViewModel() {

    private val _raspStateList = MutableLiveData<List<RaspState>>()
    val raspStateList: LiveData<List<RaspState>>
        get() = _raspStateList

    private val realDate = OffsetDateTime.now()
    fun todayDate() = "${realDate.year}-${realDate.monthValue}-${realDate.dayOfMonth}"

    fun yesterdayDate(): String {
        val date = realDate.minusDays(1)
        return "${date.year}-${date.monthValue}-${date.dayOfMonth}"
    }

    fun twoDaysAgoDate(): String {
        val date = realDate.minusDays(2)
        return "${date.year}-${date.monthValue}-${date.dayOfMonth}"
    }

    fun getRaspStateListByDate(date: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _raspStateList.postValue(getRaspStateListByDateUseCase.invoke(date))
        }
}