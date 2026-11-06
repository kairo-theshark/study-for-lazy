package com.artem.studyforlazy.model

data class Lesson(
    val id: String,
    val title: String,
    val type: String,
    val room: String,
    val startTime: String,
    val endTime: String,
    val dayOfWeek: Int,
    val isOddWeek: Boolean,
    val campusId: String,
)
