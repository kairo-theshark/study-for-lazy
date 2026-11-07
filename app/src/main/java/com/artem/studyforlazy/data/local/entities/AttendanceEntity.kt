package com.artem.studyforlazy.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class AttendanceEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val date: String,
    val status: String,
    val skipReason: String? = null,
    val photoPath: String? = null,
)
