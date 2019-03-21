package com.zenika.ageofdevsecops.domain.challenge

data class PlayerChallengeStatuses(
        val playerId: String,
        val score: Int,
        val challengeStatuses: List<ChallengeStatus>,
        val flagStatuses: FlagStatuses
)
