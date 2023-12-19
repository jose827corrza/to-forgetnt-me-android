package com.josedev.toforgetntme.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TaskSuccessReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        println("Task was set completed")
    }
}