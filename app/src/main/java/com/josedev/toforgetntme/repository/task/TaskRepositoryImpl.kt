package com.josedev.toforgetntme.repository.task

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : TaskRepository {

    override suspend fun getTaskById(id: String): Resource<ToDo> {
        return try {
//            val test = firestore.collection("users")
//                .document(auth.currentUser!!.uid)
//                .collection("tasks")
//                .document(id)
//                .get()
//                .await()
//                .toObject(ToDo::class.java)

            // V2.0
            val task = firestore.collection("tasks")
                .document(id)
                .get()
                .await()
                .toObject(ToDo::class.java)
            Log.d("REPO", "$task")
            Resource.Success(task)
        }catch (e: Exception){
            Resource.Error(null, e.message)
        }
    }

    override suspend fun createNewTask(task: ToDo) {
        try {
//            firestore.collection("users")
//                .document(auth.currentUser!!.uid)
//                .collection("tasks")
//                .add(task)
//                .await()

//            val newTaskRef = firestore.collection("users")
//                .document(auth.currentUser!!.uid)
//                .collection("tasks")
//                .document()

            // V2.0
            val newTaskRef = firestore.collection("tasks")
                .document()

            newTaskRef
                .set(task)
                .await()
        } catch (e: Exception){
            Log.d("REPO", "$e")
        }

    }

    override suspend fun updateTask(id: String, task: ToDo) {

        try {
//            firestore.collection("users")
//                .document(auth.currentUser!!.uid)
//                .collection("tasks")
//                .document(id)
//                .set(task) // Updates the whole document
//                .await()

            // V2.0
            firestore.collection("tasks")
                .document(id)
                .set(task) // Updates the whole document
                .await()
        } catch (e: Exception){

        }
    }
}