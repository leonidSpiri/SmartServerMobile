package ru.spiridonov.smartservermobile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.entity.Roles
import ru.spiridonov.smartservermobile.domain.entity.User
import ru.spiridonov.smartservermobile.domain.usecases.raspdevices.GetRaspDevicesUseCase
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetLastRaspStateUseCase
import ru.spiridonov.smartservermobile.domain.usecases.raspstate.GetRaspStateListUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.IsUserLoggedInUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.LoginUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.RegisterUseCase
import ru.spiridonov.smartservermobile.presentation.ui.home.HomeState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getRaspDevicesUseCase: GetRaspDevicesUseCase,
    getLastRaspStateUseCase: GetLastRaspStateUseCase
) : ViewModel() {

    private val loadingFlow = MutableSharedFlow<HomeState>()

    fun checkUserLoggedIn() =
        viewModelScope.launch(Dispatchers.IO) {
            isUserLoggedInUseCase.invoke().let { isUserLoggedIn ->
                Log.d("MainViewModel", "checkUserLoggedIn: $isUserLoggedIn")
                if (isUserLoggedIn) {
                    //updateRaspDevicesUseCase.invoke()
                    getRaspDevicesUseCase.invoke().let { raspDevices ->
                        Log.d("MainViewModel", "checkUserLoggedIn: $raspDevices")
                    }
                }
            }
        }

    val state: Flow<HomeState> = getLastRaspStateUseCase.invoke()
        .filter { it != null && it.raspState.isNotEmpty() }
        .map { HomeState.Content(it!!) as HomeState }
        .onStart { emit(HomeState.Loading) }
        .mergeWith(loadingFlow)

    fun register(
        username: String,
        password: String,
        email: String,
        roles: Set<Roles> = setOf(Roles.ROLE_USER)
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            registerUseCase.invoke(
                User(
                    userName = username,
                    password = password,
                    email = email,
                    roles = roles,
                    id = 0L
                )
            ).also { user ->
                Log.d("MainViewModel", "register: $user")
            }
        }

    fun login(username: String, password: String) =
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.invoke(
                email = username,
                password = password,

                ).also { user ->
                Log.d("MainViewModel", "login: $user")
            }
        }

    private fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
        return merge(this, another)
    }
}