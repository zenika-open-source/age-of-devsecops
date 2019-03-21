package com.zenika.ageofdevsecops.domain.challenge.event

import com.zenika.ageofdevsecops.domain.challenge.PlayerChallengeStatuses

data class PlayerChallengeStatusesUpdated(val challengeStatuses: Set<PlayerChallengeStatuses>) : Event
