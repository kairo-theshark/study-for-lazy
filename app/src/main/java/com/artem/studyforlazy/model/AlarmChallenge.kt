package com.artem.studyforlazy.model

/** Фаза 2 — рандомный «босс» при звонке будильника */
enum class WakeUpBossType { QR_CODE, HOUSEHOLD_OBJECT }

data class AlarmConfig(
    val isEnabled: Boolean = true,
    val alarmTime: String = "07:30",
    val minBufferMinutesBeforeFirstClass: Int = 60,
    val mathProblemsCount: Int = 2,
    val allowQrBoss: Boolean = true,
    val allowObjectBoss: Boolean = true,
)
