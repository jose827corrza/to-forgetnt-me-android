package com.josedev.toforgetntme.repository

import com.josedev.toforgetntme.domain.dto.ToDoDTO
import com.josedev.toforgetntme.domain.entity.ToDo

sealed interface TaskEvent{
    data class GetTaskById(val id: String): TaskEvent
    data class CreateNewTask(val task: ToDoDTO): TaskEvent
    data class UpdateTask(val id: String, val task: ToDoDTO): TaskEvent
}