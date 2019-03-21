package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player

interface ChallengeChecker {
    fun check(player: Player): Boolean
}
