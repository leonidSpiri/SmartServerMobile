package ru.spiridonov.smartservermobile.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.entity.Security
import ru.spiridonov.smartservermobile.domain.usecases.raspdevices.GetRaspDevicesUseCase
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetLastRaspStateUseCase
import ru.spiridonov.smartservermobile.domain.usecases.security.GetLastSecurityUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.GetAccessTokenUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.IsUserLoggedInUseCase
import ru.spiridonov.smartservermobile.presentation.ui.home.HomeState
import ru.spiridonov.smartservermobile.workers.SecurityWorker
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getRaspDevicesUseCase: GetRaspDevicesUseCase,
    getLastRaspStateUseCase: GetLastRaspStateUseCase,
    getLastSecurityUseCase: GetLastSecurityUseCase
) : ViewModel() {

    private val _mainActivityState = MutableLiveData<MainActivityState>()
    val mainActivityState: LiveData<MainActivityState>
        get() = _mainActivityState


    fun init() {
        checkUserLoggedIn()
    }

    private fun checkUserLoggedIn() =
        viewModelScope.launch(Dispatchers.IO) {
            isUserLoggedInUseCase.invoke().let { isUserLoggedIn ->
                if (isUserLoggedIn) {
                    if (getAccessTokenUseCase.invoke() == null)
                        _mainActivityState.postValue(MainActivityState.NeedToReLogin)
                    else {
                        getRaspDevicesUseCase.invoke()
                        _mainActivityState.postValue(MainActivityState.SetupView)
                        startSecurityWorker()
                    }
                } else
                    _mainActivityState.postValue(MainActivityState.NeedToReLogin)
            }
        }

    val raspStateFlow: Flow<HomeState> = getLastRaspStateUseCase.invoke()
        .filter { it != null && it.raspState.isNotEmpty() }
        .map { HomeState.Content(it!!) as HomeState }
        .onStart { emit(HomeState.Loading) }

    val securityStateFlow: Flow<Security> = getLastSecurityUseCase.invoke()
        .filter { it != null }
        .map { it!! }

    private fun startSecurityWorker() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            SecurityWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            SecurityWorker.makeRequest()
        )
    }
}