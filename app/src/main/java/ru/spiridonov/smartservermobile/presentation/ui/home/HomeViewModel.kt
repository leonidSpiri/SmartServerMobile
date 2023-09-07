package ru.spiridonov.smartservermobile.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.entity.DevTypes
import ru.spiridonov.smartservermobile.domain.entity.RaspDevices
import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetRequiredTempUseCase
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.SetNewRaspStateUseCase
import ru.spiridonov.smartservermobile.domain.usecases.security.SetSecurityUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getRequiredTempUseCase: GetRequiredTempUseCase,
    private val setNewRaspStateUseCase: SetNewRaspStateUseCase,
    private val setSecurityUseCase: SetSecurityUseCase
) : ViewModel() {

    private val _requiredTemp = MutableLiveData<Int>()
    val requiredTemp: LiveData<Int>
        get() = _requiredTemp

    fun getRequiredTemp() {
        viewModelScope.launch(Dispatchers.IO) {
            getRequiredTempUseCase.invoke().let {
                Log.d("getRequiredTemp", it.toString())
                _requiredTemp.postValue(it)
            }
        }
    }

    fun setNewSecurity(isTurnOn: Boolean = true) = viewModelScope.launch(Dispatchers.IO) {
        setSecurityUseCase.invoke(isTurnOn)
    }

    fun setNewRequiredState(
        newTemp: String?,
        isFanWorkRoot: Boolean = false,
        isCondWorkRoot: Boolean = false,
        raspState: RaspState
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newRaspState = mutableListOf<Pair<RaspDevices, String>>()
            raspState.raspState.forEach {
                when (it.first.devType) {
                    DevTypes.TEMP_SENSOR -> newRaspState.add(
                        Pair(
                            first = it.first,
                            second = newTemp ?: it.second
                        )
                    )

                    DevTypes.FAN -> newRaspState.add(
                        Pair(
                            first = it.first,
                            second = isFanWorkRoot.toString()
                        )
                    )

                    DevTypes.CONDITIONER -> newRaspState.add(
                        Pair(
                            first = it.first,
                            second = isCondWorkRoot.toString()
                        )
                    )

                    else -> newRaspState.add(it)
                }
            }
            setNewRaspStateUseCase.invoke(raspState.copy(raspState = newRaspState))
            getRequiredTemp()
        }
    }
}