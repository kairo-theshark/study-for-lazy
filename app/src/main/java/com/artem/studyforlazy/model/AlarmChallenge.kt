package com.artem.studyforlazy.model

enum class AlarmChallengeType { MATH, QR_CODE, PHOTO }

data class AlarmConfig(
    val isEnabled: Boolean,
    val challengeType: AlarmChallengeType,
)
