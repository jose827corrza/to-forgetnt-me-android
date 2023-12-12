package com.josedev.toforgetntme.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josedev.toforgetntme.domain.entity.LoginState
import com.josedev.toforgetntme.repository.auth.AuthenticationRepositoryImpl
import com.josedev.toforgetntme.repository.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepositoryImpl
) : ViewModel(){

    val _state = MutableStateFlow(LoginState())

//
    val state: StateFlow<LoginState> = _state
//
    fun changeTestState(text: String) {
        _state.value = LoginState(test= text)
    }
    fun onEvent(event: LoginEvent) {
        when(event){
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    authenticationRepository.loginUser(event.email, event.password)
                    val isUser = authenticationRepository.getUser()
                    if(isUser != null){
                        _state.update {
                            it.copy(
                                user = isUser,
                                isLoading = true
                            )
                        }
                    }else{
                    }
                }
            }

            is LoginEvent.SignUp -> {
                viewModelScope.launch {
                    authenticationRepository.createUser(event.email, event.password)
                    val isUser = authenticationRepository.getUser()
                    if(isUser != null){
                        _state.update {
                            it.copy(
                                user = isUser,
                                isLoading = true
                            )
                        }
                    }else{
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