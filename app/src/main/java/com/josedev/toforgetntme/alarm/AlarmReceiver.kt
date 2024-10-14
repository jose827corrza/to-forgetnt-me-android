package com.josedev.toforgetntme.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.josedev.toforgetntme.notification.TaskNotificationService
import java.time.LocalTime

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskNotification = TaskNotificationService(context)

        val title = intent.getStringExtra("TITLE") ?: ""
        val content = intent.getStringExtra("CONTENT") ?: ""
        val taskId = intent.getStringExtra("TASK_ID") ?: ""

        Log.d("AlarmReceiver", "Not for task with ID: $taskId")

        taskNotification.showNotification(title, content, taskId)
    }
}