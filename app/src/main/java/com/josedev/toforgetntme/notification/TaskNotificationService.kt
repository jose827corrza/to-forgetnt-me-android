package com.josedev.toforgetntme.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.josedev.toforgetntme.MainActivity
import com.josedev.toforgetntme.R

class TaskNotificationService(
    private val context: Context
): TaskScheduler {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    override fun showNotification(title: String, content: String, taskId: String) {
        val completeIntent = Intent(context, TaskSuccessReceiver::class.java).apply {
            putExtra("TASK_ID", taskId)
        }
        val cancel = Intent(context, TaskSuccessReceiver::class.java)
        val notification = NotificationCompat.Builder(context, "random_id")
            .setSmallIcon(R.mipmap.ic_launcher_outlined)
            .setContentTitle(title)
            .setContentText(content)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setContentIntent(pendingIntent)
//            .addAction(
//                R.mipmap.ic_launcher,
//                "Task Complete",
//                PendingIntent.getBroadcast(
//                    context,
//                    taskId.hashCode(),
//                    completeIntent,
//                    PendingIntent.FLAG_IMMUTABLE
//                )
//            )
//            .addAction(
//                R.mipmap.ic_launcher,
//                "Cancel",
//                PendingIntent.getBroadcast(
//                    context,
//                    1,
//                    cancel,
//                    PendingIntent.FLAG_IMMUTABLE
//                )
//            )
            .build()

        notificationManager.notify(1, notification)
    }
}