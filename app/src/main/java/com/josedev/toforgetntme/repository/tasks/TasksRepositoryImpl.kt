package com.josedev.toforgetntme.repository.tasks

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.utils.Resource
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TasksRepository {
    override suspend fun getAllUserTasks(): Resource<MutableList<ToDo>> {
        try {
            val tasks: MutableList<ToDo> = mutableListOf()
//            val querySnapshot = firestore.collection("users")
//                .document(auth.currentUser!!.uid)
//                .collection("tasks")
//                .get()
//                .await()
            // V2.0
            val querySnapshot = firestore.collection("tasks")
                .whereEqualTo("userId", auth.currentUser!!.uid)
                .get()
                .await()
            for (task in querySnapshot.documents) {
                var todoTask = task.toObject(ToDo::class.java)
                todoTask?.userId = task.id
                if (todoTask != null) {
                    tasks.add(todoTask)
                }
            }
            return Resource.Success(tasks)
        } catch (e: Exception) {
            return Resource.Error(null, e.message)
        }
    }

    override suspend fun deleteATaskById(id: String) {
        try {
//            firestore.collection("users")
//                .document(auth.currentUser!!.uid)
//                .collection("tasks")
//                .document(id)
//                .delete()
//                .await()

            // V 2.0
            firestore.collection("tasks")
                .document(id)
                .delete()
                .await()

        }catch (e: Exception){

        }
    }

}