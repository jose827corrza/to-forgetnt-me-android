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


            // V2.0
            val task = firestore.collection("tasks")
                .document(id)
                .get()
                .await()
                .toObject(ToDo::class.java)

            Resource.Success(task)
        }catch (e: Exception){
            Resource.Error(null, e.message)
        }
    }

    override suspend fun createNewTask(task: ToDo): Resource<String> {
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
            return Resource.Success(newTaskRef.id)
        } catch (e: Exception){
            return Resource.Error(null, e.message)
        }

    }

    override suspend fun updateTask(id: String, task: ToDo): Resource<String> {

        try {

            // V2.0
            val taskRef = firestore.collection("tasks")
                .document(id)

            taskRef
                .set(task) // Updates the whole document
                .await()
            return Resource.Success(taskRef.id)
        } catch (e: Exception){
            return Resource.Error(null, e.message)
        }
    }

    override suspend fun setTaskAsComplete(id: String) {
        firestore.collection("tasks")
            .document(id)
            .update("complete", true)
            .await()
    }
}