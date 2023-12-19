package com.josedev.toforgetntme.notification

interface TaskScheduler {

    fun showNotification(title: String, content: String)
}