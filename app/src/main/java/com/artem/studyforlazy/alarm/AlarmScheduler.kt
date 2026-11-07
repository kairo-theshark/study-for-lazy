package com.artem.studyforlazy.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

interface AlarmScheduler {
    fun schedule(alarmTime: LocalTime)
    fun cancel()
}

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler {

    private val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun buildPendingIntent(): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(
            context, REQUEST_CODE, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    override fun schedule(alarmTime: LocalTime) {
        // На Android 12+ проверяем, разрешены ли точные будильники
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !manager.canScheduleExactAlarms()) {
            Log.w(TAG, "canScheduleExactAlarms() = false — точный будильник недоступен")
            return
        }

        val now = ZonedDateTime.now(ZoneId.systemDefault())
        var trigger = now.with(alarmTime)
        if (!trigger.isAfter(now)) trigger = trigger.plusDays(1)

        try {
            manager.setAlarmClock(
                AlarmManager.AlarmClockInfo(trigger.toInstant().toEpochMilli(), buildPendingIntent()),
                buildPendingIntent(),
            )
            Log.d(TAG, "Будильник запланирован на $trigger")
        } catch (e: SecurityException) {
            Log.e(TAG, "SecurityException при установке будильника: ${e.message}", e)
        }
    }

    override fun cancel() {
        manager.cancel(buildPendingIntent())
        buildPendingIntent().cancel()
        Log.d(TAG, "Будильник отменён")
    }

    companion object {
        private const val TAG = "AlarmScheduler"
        private const val REQUEST_CODE = 1337
    }
}
