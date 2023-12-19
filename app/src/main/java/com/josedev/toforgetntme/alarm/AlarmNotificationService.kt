package com.josedev.toforgetntme.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.josedev.toforgetntme.domain.dto.ToDoDTO
import com.josedev.toforgetntme.domain.state.TaskState
import java.util.Calendar

class AlarmNotificationService(
    private val context: Context
): AlarmScheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(todo: ToDoDTO) {
        // Create Intent
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("TITLE", todo.name)
            putExtra("CONTENT", todo.description)
        }

        // Configure with calendar and times given in the fun parameters
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.YEAR, todo.date.year)
            set(Calendar.DAY_OF_YEAR, todo.date.dayOfYear)
            set(Calendar.HOUR_OF_DAY, todo.time.hour)
            set(Calendar.MINUTE, todo.time.minute)
        }

        // Impl el manager with setExactAndAllowWhileIdle fun -> use RTC_WAKEUP
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            PendingIntent.getBroadcast(
                context,
                todo.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }
}