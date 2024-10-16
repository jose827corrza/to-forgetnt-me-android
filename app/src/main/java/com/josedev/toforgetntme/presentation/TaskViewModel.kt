package com.josedev.toforgetntme.presentation

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.josedev.toforgetntme.alarm.AlarmNotificationService
import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.domain.state.TaskState
import com.josedev.toforgetntme.repository.TaskEvent
import com.josedev.toforgetntme.repository.task.TaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.LocalDate
import java.time.temporal.ChronoField
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepositoryImpl,
    private val auth: FirebaseAuth
): ViewModel() {


    private val _state = MutableStateFlow(TaskState())

    val state: StateFlow<TaskState> = _state.asStateFlow()



    fun onEvent(event: TaskEvent) {
        when(event){
            is TaskEvent.GetTaskById -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val task = taskRepository.getTaskById(event.id)
                    if(task.data != null){
                        _state.update {
                            it.copy(
                                taskData = task.data
                            )
                        }
                    }else if(event.id === "new") {
                        _state.update { it.copy( isLoading = false) }
                    }
                    _state.update { it.copy( isLoading = false) }
                }
            }

            is TaskEvent.CreateNewTask -> {
                val alarmManager = AlarmNotificationService(event.context)
                viewModelScope.launch(Dispatchers.IO) {
                    // Convert LocalDate to Long
                    val dueDate = LocalDate.parse(event.task.taskDate)
                    val longDueDate = dueDate.getLong(ChronoField.EPOCH_DAY)
                    val todo = ToDo(
                        event.task.title,event.task.isComplete,
                        event.task.description,
                        auth.currentUser!!.uid,
                        longDueDate,
                        event.task.taskTime,
                        event.task.taskDate
                    )
                    val taskInfo = taskRepository.createNewTask(todo)
                    if(taskInfo.data != null){
                        alarmManager.schedule(event.task, taskInfo.data)
                    }
                }
            }
            is TaskEvent.UpdateTask -> {
                val alarmManager = AlarmNotificationService(event.context)
                viewModelScope.launch(Dispatchers.IO) {
                    val todo = ToDo(
                        event.task.title,
                        event.task.isComplete,
                        event.task.description,
                        auth.currentUser!!.uid,
                        event.task.createdDate,
                        event.task.taskTime,
                        event.task.taskDate
                    )
                    val taskInfo = taskRepository.updateTask(event.id, todo)

                    if(taskInfo.data != null){
                        alarmManager.cancel(taskInfo.data)
                        alarmManager.schedule(event.task, taskInfo.data)
                    }
                }
            }
        }
    }

}