package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.stereotype.Component

@Component
class SonarChallengeChecker(private val sonarRepository: SonarChecker) : ChallengeChecker {

    override fun check(player: Player): Boolean {
        return try {
            sonarRepository.getReport(player)
            true
        } catch (e: Exception) {
            false
        }
    }

}
