package com.artem.studyforlazy.model

enum class TaskPriority { LOW, MEDIUM, HIGH, CRITICAL }

data class DeadlineTask(
    val id: String,
    val title: String,
    val subject: String,
    val dueDate: String,
    val priority: TaskPriority,
    val isCompleted: Boolean = false,
)
