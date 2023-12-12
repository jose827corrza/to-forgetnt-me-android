package com.josedev.toforgetntme.repository.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthenticationRepository {
    fun createUser(email: String, password: String)
    suspend fun loginUser(email: String, password: String)
    fun signOut()
    fun getUser(): FirebaseUser?
}