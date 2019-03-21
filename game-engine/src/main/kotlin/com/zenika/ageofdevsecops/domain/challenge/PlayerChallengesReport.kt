package com.zenika.ageofdevsecops.domain.challenge

data class PlayerChallengesReport(
        val challengeStatuses: List<ChallengeStatus>,
        val flagStatuses: FlagStatuses
)
