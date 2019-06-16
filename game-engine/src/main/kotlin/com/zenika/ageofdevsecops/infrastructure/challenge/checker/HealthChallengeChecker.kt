package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.stereotype.Component

//@Component
class HealthChallengeChecker(private val healthChecker: HealthChecker) : ChallengeChecker {

    override fun check(player: Player): Boolean {
        return try {
            val status = healthChecker.verify(player.main.url)
            status.status == "UP"
        } catch (e: Exception) {
            false
        }
    }

}



