package com.artem.studyforlazy.data

import com.artem.studyforlazy.data.local.entities.DeadlineEntity
import com.artem.studyforlazy.data.local.entities.LessonEntity
import com.artem.studyforlazy.model.DeadlineTask
import com.artem.studyforlazy.model.Lesson
import com.artem.studyforlazy.model.TaskPriority

fun LessonEntity.toModel() = Lesson(
    id = id,
    title = title,
    type = type,
    room = room,
    startTime = startTime,
    endTime = endTime,
    dayOfWeek = dayOfWeek,
    isOddWeek = isOddWeek,
    campusId = campusId,
)

fun DeadlineEntity.toModel() = DeadlineTask(
    id = id,
    title = title,
    subject = subject,
    dueDate = dueDate,
    priority = TaskPriority.valueOf(priority),
    isCompleted = isCompleted,
)
