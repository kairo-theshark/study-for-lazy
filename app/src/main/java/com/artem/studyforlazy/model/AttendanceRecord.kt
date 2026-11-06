package com.artem.studyforlazy.model

enum class AttendanceStatus { ATTENDED, SKIPPED }

data class AttendanceRecord(
    val id: String,
    val lessonId: String,
    val date: String,
    val status: AttendanceStatus,
    val skipReason: String? = null,
    val photoPath: String? = null,
)
