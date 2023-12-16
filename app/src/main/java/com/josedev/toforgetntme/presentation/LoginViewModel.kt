package com.josedev.toforgetntme.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josedev.toforgetntme.domain.state.LoginState
import com.josedev.toforgetntme.repository.auth.AuthenticationRepositoryImpl
import com.josedev.toforgetntme.repository.LoginEvent
import com.josedev.toforgetntme.repository.tasks.TasksRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepositoryImpl,
    private val tasksRepositoryImpl: TasksRepositoryImpl,
) : ViewModel(){

    val _state = MutableStateFlow(LoginState())

//
    val state: StateFlow<LoginState> = _state.asStateFlow()


    fun onEvent(event: LoginEvent) {
        when(event){
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    val fireUser = authenticationRepository.loginUser(event.email, event.password)

                    // If there is a message, there were an error
                    if(fireUser.message != null){
                        Log.d("AuthVM", "There is message")
                        _state.update {
                            it.copy(
                                isError = true
                            )
                        }
                        delay(1000L)
                        _state.update {
                            it.copy(
                                isError = false
                            )
                        }
                        return@launch
                    }

                    if(fireUser.data != null){
                        Log.d("AuthVM", "Enter the is")
                        _state.update {
                            it.copy(
                                user = authenticationRepository.getUser(),
                                isLoading = true
                            )
                        }
                    }else{
                        Log.d("AuthVM", "Else caught me")
                        _state.update {
                            it.copy(
                                isError = true
                            )
                        }
                        delay(1000L)
                        _state.update {
                            it.copy(
                                isError = false
                            )
                        }
                    }
                }
            }

            is LoginEvent.SignUp -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val userAuth = authenticationRepository.createUser(event.email, event.password)

                    // If there is a message, there were an error
                    if(userAuth.message != null){
                        Log.d("AuthVM", "There is message")
                        _state.update {
                            it.copy(
                                isError = true
                            )
                        }
                        delay(1000L)
                        _state.update {
                            it.copy(
                                isError = false
                            )
                        }
                        return@launch
                    }

                    // If there is data, make the process
                    if(userAuth.data != null){
                        tasksRepositoryImpl.createFirstTaskForNewUser()
                        _state.update {
                            it.copy(
                                user = authenticationRepository.getUser(),
                                isLoading = true
                            )
                        }

                    }else{
                        Log.d("AuthVM", "Else caught me")
                        _state.update {
                            it.copy(
                                isError = true
                            )
                        }
                        delay(1000L)
                        _state.update {
                            it.copy(
                                isError = false
                            )
                        }
                    }
                }
            }

            is LoginEvent.SignOut -> {
                viewModelScope.launch {
                    authenticationRepository.signOut()
                    _state.update {
                        it.copy(
                            user = null
                        )
                    }
                }
            }

        }
    }
}