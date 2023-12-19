package com.josedev.toforgetntme.domain.state

import com.josedev.toforgetntme.domain.entity.ToDo
import java.time.LocalDate
import java.time.LocalTime

data class TaskState(
    val taskData: ToDo = ToDo("", false, "", "","",""),
    val isLoading: Boolean = true,
    val taskDate: LocalDate = LocalDate.now(),
    val taskTime: LocalTime = LocalTime.now()
)
