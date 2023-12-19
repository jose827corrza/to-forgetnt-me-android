package com.josedev.toforgetntme.domain.entity

import java.time.LocalDate
import java.time.LocalTime

data class ToDo(
    val name: String,
    val isComplete: Boolean,
    val description: String,
    var userId: String,
    val taskTime: String,
    val taskDate: String
){
    constructor(): this("", false,"", "", "","")
}
