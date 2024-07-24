package com.josedev.toforgetntme.repository.task

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.utils.Resource
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : TaskRepository {

    override suspend fun getTaskById(id: String): Resource<ToDo> {
        return try {
            // v3.0
            val taskNew = firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("todos")
                .document(id)
                .get()
                .await()
                .toObject(ToDo::class.java)

            // V2.0
//            val task = firestore.collection("tasks")
//                .document(id)
//                .get()
//                .await()
//                .toObject(ToDo::class.java)

            Resource.Success(taskNew)
        }catch (e: Exception){
            Resource.Error(null, e.message)
        }
    }

    override suspend fun createNewTask(task: ToDo): Resource<String> {
        try {
            //v3.0
            val newUUID = UUID.randomUUID().toString()
            val newTaskRefNew = firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("todos")
                .document(newUUID)
            newTaskRefNew
                .set(task)
                .await()

            // V2.0
//            val newTaskRef = firestore.collection("tasks")
//                .document()
//
//            newTaskRef
//                .set(task)
//                .await()
            return Resource.Success(newTaskRefNew.id)
        } catch (e: Exception){
            return Resource.Error(null, e.message)
        }

    }

    override suspend fun updateTask(id: String, task: ToDo): Resource<String> {

        try {
            //v3.0
            val taskRefNew = firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("todos")
                .document(id)
            taskRefNew
                .set(task)
                .await()

            // V2.0
//            val taskRef = firestore.collection("tasks")
//                .document(id)
//
//            taskRef
//                .set(task) // Updates the whole document
//                .await()
            return Resource.Success(taskRefNew.id)
        } catch (e: Exception){
            return Resource.Error(null, e.message)
        }
    }

    override suspend fun setTaskAsComplete(id: String) {
        //v3.0
        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("todos")
            .document(id)
            .update("complete", true)
            .await()

        //v2.0
//        firestore.collection("tasks")
//            .document(id)
//            .update("complete", true)
//            .await()
    }
}