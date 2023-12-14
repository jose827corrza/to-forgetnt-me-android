package com.josedev.toforgetntme.domain.entity

data class HomeState(
    val taskList: List<ToDo> = emptyList(),
    var isLoading: Boolean = true
)
