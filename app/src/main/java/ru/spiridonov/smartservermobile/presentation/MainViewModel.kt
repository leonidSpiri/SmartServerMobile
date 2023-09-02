package ru.spiridonov.smartservermobile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiridonov.smartservermobile.domain.entity.Roles
import ru.spiridonov.smartservermobile.domain.entity.User
import ru.spiridonov.smartservermobile.domain.usecases.user.GetUserUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.IsUserLoggedInUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.LoginUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.RegisterUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    fun checkUserLoggedIn() =
        viewModelScope.launch(Dispatchers.IO) {
            isUserLoggedInUseCase.invoke().let { isUserLoggedIn ->
                Log.d("MainViewModel", "checkUserLoggedIn: $isUserLoggedIn")
                if (isUserLoggedIn)
                    getUserUseCase.invoke().let { user ->
                        Log.d("MainViewModel", "getUserUseCase: $user")
                    }
            }
        }

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
}