package com.josedev.toforgetntme.repository.tasks

import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.utils.Resource

interface TaskRepository {

    suspend fun getAllUserTasks(): Resource<MutableList<ToDo>>
    suspend fun getTaskById(id: String): Resource<ToDo>
}