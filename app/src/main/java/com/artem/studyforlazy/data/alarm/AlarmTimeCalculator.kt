package com.artem.studyforlazy.data.alarm

import com.artem.studyforlazy.data.local.dao.ScheduleDao
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Вычисляет максимально допустимое время подъёма с учётом первой пары на завтра.
 * Возвращает `null`, если пар нет — ограничений нет.
 */
class AlarmTimeCalculator(private val dao: ScheduleDao) {

    private val timeFmt = DateTimeFormatter.ofPattern("HH:mm")

    suspend fun latestAllowedWakeUp(bufferMinutes: Int): LocalTime? {
        val tomorrow = LocalDate.now().plusDays(1)
        val dayOfWeek = tomorrow.dayOfWeek.value  // 1=Mon…7=Sun, совпадает с БД
        val isOddWeek = isOddWeek(tomorrow)

        val lessons = dao.getLessonsOnce(dayOfWeek, isOddWeek)
        val firstLesson = lessons.minByOrNull { it.startTime } ?: return null

        val firstStart = LocalTime.parse(firstLesson.startTime, timeFmt)
        return firstStart.minus(bufferMinutes.toLong(), ChronoUnit.MINUTES)
    }

    private fun isOddWeek(date: LocalDate): Boolean {
        // Неделя 1 от начала года нечётная
        val weekNumber = date.get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear())
        return weekNumber % 2 != 0
    }
}
