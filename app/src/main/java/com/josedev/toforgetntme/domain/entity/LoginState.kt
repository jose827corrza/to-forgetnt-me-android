package com.josedev.toforgetntme.domain.entity

import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val user: FirebaseUser? = null,
    val isLoading: Boolean = false
)
