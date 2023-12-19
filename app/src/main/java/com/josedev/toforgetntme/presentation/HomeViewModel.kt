package com.josedev.toforgetntme.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josedev.toforgetntme.domain.state.HomeState
import com.josedev.toforgetntme.repository.HomeEvent
import com.josedev.toforgetntme.repository.tasks.TasksRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TasksRepositoryImpl
): ViewModel(){

    private var _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            onEvent(HomeEvent.GetAllTasks)
        }
    }

    fun onEvent(event: HomeEvent){
        when(event){
            HomeEvent.GetAllTasks -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val tasks = taskRepository.getAllUserTasks()
                    if(tasks.data != null){
                        _state.update {
                            it.copy(
                                taskList = tasks.data
                            )
                        }
                        _state.update { it.copy( isLoading = false) }
                    }
                }

            }

            is HomeEvent.DeleteATaskById -> {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.deleteATaskById(event.id)
                    val tasks =taskRepository.getAllUserTasks()
                    if(tasks.data != null){
                        _state.update {
                            it.copy(
                                taskList = tasks.data
                            )
                        }
//                        _state.update { it.copy( isLoading = false) }
                    }
                }
            }
        }
    }

}