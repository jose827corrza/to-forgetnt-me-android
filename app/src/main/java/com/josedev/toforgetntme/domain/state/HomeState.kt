package com.josedev.toforgetntme.domain.state

import com.josedev.toforgetntme.domain.entity.ToDo

data class HomeState(
    val taskList: List<ToDo> = emptyList(),
    var isLoading: Boolean = true
)
