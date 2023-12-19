package com.josedev.toforgetntme.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TaskFailReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        println("Cancel")
    }
}