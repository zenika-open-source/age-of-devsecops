package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.stereotype.Component

@Component
class QualityCodeChallengeChecker(private val sonarRepository: SonarChecker) : ChallengeChecker {

    override fun check(player: Player): Boolean {
        return try {
            val report = sonarRepository.getReport(player)
            return report.bugs == 0 && report.codeSmells == 0 && report.coverage>80 && report.linesOfCode>250
        } catch (e: Exception) {
            false
        }
    }

}
