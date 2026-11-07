package com.artem.studyforlazy.data.repository

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleJson(
    val campuses: List<CampusDto> = emptyList(),
    val weeks: WeeksDto = WeeksDto(),
)

@Serializable
data class CampusDto(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radiusMeters: Int = 200,
)

@Serializable
data class WeeksDto(
    val odd: List<DayDto> = emptyList(),
    val even: List<DayDto> = emptyList(),
)

@Serializable
data class DayDto(
    val dayOfWeek: Int,
    val classes: List<ClassDto> = emptyList(),
)

@Serializable
data class ClassDto(
    val title: String,
    val type: String,
    val startTime: String,
    val endTime: String,
    val room: String,
    val campusId: String,
)
