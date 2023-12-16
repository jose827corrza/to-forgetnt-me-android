package com.josedev.toforgetntme.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepositoryImpl
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
                viewModelScope.launch(Dispatchers.IO) {
                    Log.d("TaskVM", "date: ${event.task.taskDate} ... time: ${event.task.taskTime}")
                    val todo = ToDo(event.task.name,event.task.isComplete,event.task.description, null)
                    taskRepository.createNewTask(todo)
                }
            }
            is TaskEvent.UpdateTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val todo = ToDo(event.task.name,event.task.isComplete,event.task.description, null)
                    taskRepository.updateTask(event.id, todo)
                }
            }
        }
    }

}