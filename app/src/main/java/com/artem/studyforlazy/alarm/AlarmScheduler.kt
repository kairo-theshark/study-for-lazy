package com.artem.studyforlazy.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        var trigger = now.with(alarmTime)
        if (!trigger.isAfter(now)) trigger = trigger.plusDays(1)
        manager.setAlarmClock(
            AlarmManager.AlarmClockInfo(trigger.toInstant().toEpochMilli(), buildPendingIntent()),
            buildPendingIntent(),
        )
    }

    override fun cancel() {
        manager.cancel(buildPendingIntent())
        buildPendingIntent().cancel()
    }

    companion object {
        private const val REQUEST_CODE = 1337
    }
}
