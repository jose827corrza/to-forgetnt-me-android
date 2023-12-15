package com.josedev.toforgetntme.repository

sealed interface HomeEvent{
    object GetAllTasks: HomeEvent
    data class DeleteATaskById(val id: String): HomeEvent
}