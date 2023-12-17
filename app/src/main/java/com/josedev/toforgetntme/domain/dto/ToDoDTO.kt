package com.josedev.toforgetntme.domain.dto

import java.time.LocalDate
import java.time.LocalTime

data class ToDoDTO(
    val name: String,
    val description: String,
    val isComplete: Boolean,
    val taskDate: String,
    val taskTime: String
)
