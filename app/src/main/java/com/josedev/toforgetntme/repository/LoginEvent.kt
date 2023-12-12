package com.josedev.toforgetntme.repository

sealed interface LoginEvent{
    data class Login(val email: String, val password: String): LoginEvent
    data class SignUp(val email: String, val password: String): LoginEvent
    object SignOut: LoginEvent
}