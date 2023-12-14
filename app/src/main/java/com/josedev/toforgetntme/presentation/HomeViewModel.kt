package com.josedev.toforgetntme.presentation


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josedev.toforgetntme.domain.entity.HomeState
import com.josedev.toforgetntme.repository.HomeEvent
import com.josedev.toforgetntme.repository.tasks.TaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepositoryImpl
): ViewModel(){

    private var _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        viewModelScope.launch {
            onEvent(HomeEvent.getAlltasks)
        }
    }

    fun onEvent(event: HomeEvent){
        when(event){
            HomeEvent.getAlltasks -> {
                viewModelScope.launch {
                    val test = taskRepository.getAllTasksAwait()
                    _state.update {
                        it.copy(
                            taskList = test
                        )
                    }
                    _state.value.isLoading = false
                }

            }
            is HomeEvent.getTaskById -> {
                viewModelScope.launch {
                    val test = taskRepository.getTaskById(event.id)
                    Log.d("HVM", "${test.data}")
                }
            }
        }
    }

}