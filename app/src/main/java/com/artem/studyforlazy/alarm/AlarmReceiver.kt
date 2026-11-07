package com.artem.studyforlazy.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.artem.studyforlazy.ui.alarm.AlarmActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // 1. Запускаем foreground-сервис со звуком и вибрацией
        val serviceIntent = Intent(context, AlarmService::class.java)
        context.startForegroundService(serviceIntent)

        // 2. Запускаем экран будильника поверх экрана блокировки
        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        context.startActivity(activityIntent)
    }
}
