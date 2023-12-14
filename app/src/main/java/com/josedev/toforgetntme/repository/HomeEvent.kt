package com.josedev.toforgetntme.repository

sealed interface HomeEvent{
    object getAlltasks: HomeEvent
    data class getTaskById(val id: String): HomeEvent
}