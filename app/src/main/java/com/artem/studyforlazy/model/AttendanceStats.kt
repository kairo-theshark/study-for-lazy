package com.artem.studyforlazy.model

data class AttendanceStats(
    val totalClasses: Int,
    val attendedClasses: Int,
    val skippedClasses: Int,
) {
    val attendancePercent: Int
        get() = if (totalClasses == 0) 0 else attendedClasses * 100 / totalClasses
}
