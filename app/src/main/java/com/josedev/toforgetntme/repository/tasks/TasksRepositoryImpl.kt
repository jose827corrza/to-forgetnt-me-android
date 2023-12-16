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
            val querySnapshot = firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("tasks")
                .get()
                .await()
            for (task in querySnapshot.documents) {
                var todoTask = task.toObject(ToDo::class.java)
                todoTask?.id = task.id
                if (todoTask != null) {
                    tasks.add(todoTask)
                    Log.d("ID", "$todoTask")
                }
            }
            return Resource.Success(tasks)
        } catch (e: Exception) {
            return Resource.Error(null, e.message)
        }
    }

    override suspend fun deleteATaskById(id: String) {
        try {
            firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("tasks")
                .document(id)
                .delete()
                .await()
        }catch (e: Exception){

        }
    }

    override suspend fun createFirstTaskForNewUser() {
        try {
            val firstTodo = ToDo("My first Todo", false, "Your first task created in your DB", null)
            val newTaskRef = firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("tasks")
                .document()

            newTaskRef
                .set(firstTodo)
                .await()
            Log.d("TasksREPO", "Should created till here: ${newTaskRef.id}")
            firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .update("email", auth.currentUser!!.email)
                .await()
            Log.d("TasksREPO", "Finish process")
        }catch (e: Exception){
            Log.d("TasksREPO", "$e")
        }

    }

}