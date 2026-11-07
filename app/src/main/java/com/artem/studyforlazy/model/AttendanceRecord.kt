package com.artem.studyforlazy.model

enum class AttendanceStatus { ATTENDED, SKIPPED }

// состояние дня для SD-чипа: от него зависит полная/половинчатая заливка
enum class DayAttendanceState { FUTURE, NONE, FULL_ATTENDED, PARTIAL_SKIPPED, FULL_SKIPPED }

data class AttendanceRecord(
    val id: String,
    val lessonId: String,
    val date: String,
    val status: AttendanceStatus,
    val skipReason: String? = null,
    val photoPath: String? = null,
)
