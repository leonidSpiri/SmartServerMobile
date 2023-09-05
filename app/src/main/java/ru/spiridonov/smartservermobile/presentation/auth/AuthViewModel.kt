package ru.spiridonov.smartservermobile.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.entity.Roles
import ru.spiridonov.smartservermobile.domain.entity.User
import ru.spiridonov.smartservermobile.domain.usecases.user.LoginUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.RegisterUseCase
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean>
        get() = _loginStatus

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
                _loginStatus.postValue(user != null)
            }
        }

    fun login(username: String, password: String) =
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.invoke(
                email = username,
                password = password,

                ).also { user ->
                Log.d("MainViewModel", "login: $user")
                _loginStatus.postValue(user != null)
            }
        }
}