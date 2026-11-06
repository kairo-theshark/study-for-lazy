package com.artem.studyforlazy.model

data class Campus(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radiusMeters: Int = 200,
)
