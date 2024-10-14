package com.josedev.toforgetntme.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.josedev.toforgetntme.repository.task.TaskRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskSuccessReceiver @Inject constructor(
// private val taskRepository: TaskRepositoryImpl
): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getStringExtra("TASK_ID")
        println("Task was set completed")
//        println("Task: $taskId set completed before If")
        if(taskId != null){
            CoroutineScope(Dispatchers.IO).launch {
//                if(taskId.isNotEmpty()){
//                    taskRepository.setTaskAsComplete(taskId)
//                }
                println("Task: $taskId set completed")
        }

        }
    }

}