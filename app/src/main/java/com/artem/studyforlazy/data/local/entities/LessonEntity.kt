package com.artem.studyforlazy.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey val id: String,
    val title: String,
    val type: String,
    val startTime: String,
    val endTime: String,
    val room: String,
    val campusId: String,
    val dayOfWeek: Int,
    val isOddWeek: Boolean,
)
