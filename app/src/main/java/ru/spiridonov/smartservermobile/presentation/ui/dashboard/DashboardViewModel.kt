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
    fun todayDate() = parseDate(realDate)

    fun yesterdayDate() = parseDate(realDate.minusDays(1))

    fun twoDaysAgoDate() = parseDate(realDate.minusDays(2))

    private fun parseDate(date: OffsetDateTime): String {
        val month =
            if (date.monthValue >= 10) date.monthValue.toString() else "0${date.monthValue}"
        val day =
            if (date.dayOfMonth >= 10) date.dayOfMonth.toString() else "0${date.dayOfMonth}"
        return "${date.year}-$month-$day"
    }

    fun getRaspStateListByDate(date: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _raspStateList.postValue(getRaspStateListByDateUseCase.invoke(date))
        }
}