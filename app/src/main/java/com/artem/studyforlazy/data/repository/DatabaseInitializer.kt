package com.artem.studyforlazy.data.repository

import android.content.Context
import com.artem.studyforlazy.data.local.AppDatabase
import com.artem.studyforlazy.data.local.entities.CampusEntity
import com.artem.studyforlazy.data.local.entities.DeadlineEntity
import com.artem.studyforlazy.data.local.entities.LessonEntity
import com.artem.studyforlazy.model.TaskPriority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.time.LocalDate

class DatabaseInitializer(
    private val context: Context,
    private val db: AppDatabase,
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun seedIfEmpty() = withContext(Dispatchers.IO) {
        if (db.campusDao().count() > 0) return@withContext

        val raw = context.assets.open("schedule_sample.json").bufferedReader().use { it.readText() }
        val schedule = json.decodeFromString<ScheduleJson>(raw)

        db.campusDao().insertAll(schedule.campuses.map { it.toEntity() })
        db.scheduleDao().insertAll(schedule.toLessons())
        db.deadlineDao().insertAll(defaultDeadlines())
    }

    private fun CampusDto.toEntity() = CampusEntity(id, name, latitude, longitude, radiusMeters)

    private fun ScheduleJson.toLessons(): List<LessonEntity> = buildList {
        weeks.odd.forEach { day -> addAll(day.toEntities(isOddWeek = true)) }
        weeks.even.forEach { day -> addAll(day.toEntities(isOddWeek = false)) }
    }

    private fun DayDto.toEntities(isOddWeek: Boolean) = classes.mapIndexed { index, c ->
        LessonEntity(
            id = "${if (isOddWeek) "odd" else "even"}-$dayOfWeek-$index",
            title = c.title,
            type = c.type,
            startTime = c.startTime,
            endTime = c.endTime,
            room = c.room,
            campusId = c.campusId,
            dayOfWeek = dayOfWeek,
            isOddWeek = isOddWeek,
        )
    }

    private fun defaultDeadlines(): List<DeadlineEntity> {
        val today = LocalDate.now()
        return listOf(
            DeadlineEntity("seed-1", "Лаба №3: сортировки", "Алгоритмы", today.plusDays(2).toString(), TaskPriority.LOW.name),
            DeadlineEntity("seed-2", "Курсовая: первая глава", "Экономика", today.plusDays(5).toString(), TaskPriority.HIGH.name),
            DeadlineEntity("seed-3", "Комиссия по матанализу", "Мат. анализ", today.plusDays(12).toString(), TaskPriority.CRITICAL.name),
        )
    }
}
