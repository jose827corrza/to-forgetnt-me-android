package com.josedev.toforgetntme.repository.task

import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.utils.Resource

interface TaskRepository {


    suspend fun getTaskById(id: String): Resource<ToDo>
    suspend fun createNewTask(task: ToDo): Resource<String> // is task's ID
    suspend fun updateTask(id: String, task: ToDo): Resource<String>

    suspend fun setTaskAsComplete(id: String)
}