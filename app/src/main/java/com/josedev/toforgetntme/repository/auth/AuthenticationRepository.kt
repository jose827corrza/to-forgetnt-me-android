package com.josedev.toforgetntme.repository.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.josedev.toforgetntme.utils.Resource

interface AuthenticationRepository {
    suspend fun createUser(email: String, password: String): Resource<FirebaseUser>
    suspend fun loginUser(email: String, password: String): Resource<FirebaseUser>
    fun signOut()
    fun getUser(): FirebaseUser?
}