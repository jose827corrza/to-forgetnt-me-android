package com.josedev.toforgetntme.repository.tasks

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject
import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TaskRepository {
    override suspend fun getAllUserTasks(): Resource<MutableList<ToDo>> {
        TODO("Not yet implemented")
//        try {
////        val tasks : ArrayList<ToDo> = arrayListOf()
//            val tasksList: MutableList<ToDo> = mutableListOf()
//
//        val test =firestore.collection("users")
//            .document(auth.currentUser!!.uid)
//            .collection("tasks")
//            .get()
//            .addOnSuccessListener {
//                for (document in it){
//                    val task = document.toObject(ToDo::class.java)
////                    tasks.add(task)
//                    Log.d("TREPOSITORY", ":::${document.data}")
//                    tasksList.add(task)
//                }
//            }
//            if(test.isSuccessful){
//                Log.d("TREPOSITORY", "$tasksList")
//                Log.d("TREPOSITORY", "${test.result}")
//                return Resource.Success(tasksList)
//            }
//            return Resource.Error(null, "Is not ready yet")
//        } catch (e: RuntimeException){
//            return Resource.Error(null,e.message)
//        }
    }

//    suspend fun getAllTasksWithFLows(): Flow<List<ToDo>> = flow {
//        val tasksList: MutableList<ToDo> = mutableListOf()
//        val test = firestore.collection("users")
//            .document(auth.currentUser!!.uid)
//            .collection("tasks")
//            .get()
//            .addOnSuccessListener {
//                for (document in it){
//                    val task = document.toObject(ToDo::class.java)
//                    tasksList.add(task)
//                    Log.d("TREPOSITORY", ":::${document.data}")
//                }
//                return@addOnSuccessListener
//            }
////        emit(Resource.Success<List<ToDo>>(tasksList))
//        emit(tasksList)
//    }

    suspend fun getAllTasksAwait(): MutableList<ToDo> {
        try {
            var tasks: MutableList<ToDo> = mutableListOf()
            val querySnapshot = firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("tasks")
                .get()
                .await()
            for(task in querySnapshot.documents){
                var todoTask = task.toObject(ToDo::class.java)
                todoTask?.id = task.id
                if (todoTask != null) {
                    tasks.add(todoTask)
                    Log.d("ID", "$todoTask")
                }
            }
            return tasks
        }catch (e: Exception){
            return mutableListOf()
        }
    }

    override suspend fun getTaskById(id: String): Resource<ToDo> {
        try {
            val test = firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("tasks")
                .document(id)
                .get()
                .await()
                .toObject(ToDo::class.java)
            Log.d("REPO", "$test")
            return Resource.Success(test)
        }catch (e: Exception){
            return Resource.Error(null, e.message)
        }
    }
}