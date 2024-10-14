package com.josedev.toforgetntme.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.josedev.toforgetntme.domain.entity.User
import com.josedev.toforgetntme.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fire: FirebaseFirestore
) : AuthenticationRepository {
    override suspend fun createUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val t =auth.createUserWithEmailAndPassword(email, password)
                .await()
            val newUser = User(t.user!!.email!!, t.user!!.uid, t.user!!.metadata!!.creationTimestamp.toString(), "testName")

            fire.collection("users")
                .document(auth.currentUser!!.uid)
                .set(newUser)
                .await()
            Resource.Success(auth.currentUser)
        }catch (e: Exception){
            Resource.Error(null, e.message)
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(auth.currentUser)
        }catch (e: Exception){
            Resource.Error(null, e.message)
        }
    }

    override fun signOut() {
        try {
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getUser(): FirebaseUser? {
        return auth.currentUser
    }
}