package com.josedev.toforgetntme.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.josedev.toforgetntme.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthenticationRepository {
    override suspend fun createUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { result ->
                    if(result.isSuccessful){
                        Log.d("REPOSITORY", "User $email created")
                    }else {
                        Log.d("REPOSITORY", "User $email could not be created")
                    }
                }
                .await()
            Resource.Success(auth.currentUser)
        }catch (e: Exception){
            Resource.Error(null, e.message)
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d("REPOSITORY", "User $email logged")
                    }else {
                        Log.d("REPOSITORY", "User $email could not be logged")

                    }
                }.await()
            Resource.Success(auth.currentUser)
        }catch (e: Exception){
            Resource.Error(null, e.message)
        }
    }

    override fun signOut() {
       auth.signOut()
    }

    override fun getUser(): FirebaseUser? {
        return auth.currentUser
    }
}