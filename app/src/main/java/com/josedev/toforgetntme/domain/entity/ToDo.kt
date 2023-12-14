package com.josedev.toforgetntme.domain.entity

data class ToDo(
    val name: String,
    val isComplete: Boolean,
    val description: String,
    var id: String
){
    constructor(): this("", false,"","")
}
