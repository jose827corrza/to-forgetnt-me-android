package com.josedev.toforgetntme.repository.tasks

import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.utils.Resource

interface TasksRepository {

    suspend fun getAllUserTasks(): Resource<MutableList<ToDo>>

    suspend fun deleteATaskById(id: String)

    suspend fun createFirstTaskForNewUser()
}