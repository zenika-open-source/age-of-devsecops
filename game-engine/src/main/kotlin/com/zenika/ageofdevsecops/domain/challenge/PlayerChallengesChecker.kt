package com.zenika.ageofdevsecops.domain.challenge


interface PlayerChallengesChecker {
    fun onboard(player: Player): PlayerChallengesReport
    fun check(player: Player): PlayerChallengesReport
}
