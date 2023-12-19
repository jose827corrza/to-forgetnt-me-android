package com.josedev.toforgetntme.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.josedev.toforgetntme.R

class TaskNotificationService(
    private val context: Context
): TaskScheduler {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    override fun showNotification(title: String, content: String) {
        val completeIntent = Intent(context, TaskSuccessReceiver::class.java)
        val cancel = Intent(context, TaskSuccessReceiver::class.java)
        val notification = NotificationCompat.Builder(context, "random_id")
            .setSmallIcon(R.mipmap.ic_launcher_outlined)
            .setContentTitle(title)
            .setContentText(content)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .addAction(
                R.mipmap.ic_launcher,
                "Task Complete",
                PendingIntent.getBroadcast(
                    context,
                    0,
                    completeIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .addAction(
                R.mipmap.ic_launcher,
                "Cancel",
                PendingIntent.getBroadcast(
                    context,
                    1,
                    cancel,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()

        notificationManager.notify(1, notification)
    }
}