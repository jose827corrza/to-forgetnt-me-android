package com.josedev.toforgetntme.domain.state

import com.josedev.toforgetntme.domain.entity.ToDo
import java.time.LocalDate
import java.time.LocalTime

data class TaskState(
    val taskData: ToDo = ToDo("", false, "", "", LocalDate.now(), LocalTime.now()),
    val isLoading: Boolean = true
)
