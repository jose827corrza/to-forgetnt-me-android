package com.josedev.toforgetntme.repository

import com.josedev.toforgetntme.domain.entity.ToDo

sealed interface TaskEvent{
    data class GetTaskById(val id: String): TaskEvent
    data class CreateNewTask(val task: ToDo): TaskEvent
    data class UpdateTask(val id: String, val task: ToDo): TaskEvent
}