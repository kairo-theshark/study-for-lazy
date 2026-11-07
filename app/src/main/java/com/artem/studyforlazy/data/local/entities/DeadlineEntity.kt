package com.artem.studyforlazy.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deadlines")
data class DeadlineEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subject: String,
    val dueDate: String,
    val priority: String,
    val isCompleted: Boolean = false,
)
