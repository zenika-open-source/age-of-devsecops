package com.zenika.ageofdevsecops.domain.challenge.event

import com.zenika.ageofdevsecops.domain.challenge.PlayerScore

data class PlayerScoresUpdated(val scores: Set<PlayerScore>) : Event
