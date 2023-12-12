package com.josedev.toforgetntme.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthenticationRepository {
    override fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if(result.isSuccessful){
                    Log.d("REPOSITORY", "User $email created")
                }else {
                    Log.d("REPOSITORY", "User $email could not be created")
                }
            }
    }

    override suspend fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d("REPOSITORY", "User $email logged")
                }else {
                    Log.d("REPOSITORY", "User $email could not be logged")

                }
            }
    }

    override fun signOut() {
       auth.signOut()
    }

    override fun getUser(): FirebaseUser? {
        return auth.currentUser
    }
}