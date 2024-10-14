package com.josedev.toforgetntme.alarm

import com.josedev.toforgetntme.domain.dto.ToDoDTO
import com.josedev.toforgetntme.domain.state.TaskState

interface AlarmScheduler {

    fun schedule(todo: ToDoDTO, taskId: String)
    fun cancel(taskId: String)
}