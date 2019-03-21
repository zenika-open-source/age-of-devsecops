package com.zenika.ageofdevsecops.domain.challenge

class ReportGrader {
    fun grade(playerReport: PlayerChallengesReport): ReportGrade {
        var devPoints = 0
        var secPoints = 0
        var opsPoints = 0

        val completedChallenges = playerReport.challengeStatuses.filter { it.completed }
        completedChallenges.forEach {
            devPoints += it.challenge.devPoint
            secPoints += it.challenge.secPoint
            opsPoints += it.challenge.opsPoint
        }

        val flags = playerReport.flagStatuses
        devPoints += flags.devPoint
        secPoints += flags.secPoint
        opsPoints += flags.opsPoint

        return ReportGrade(minOf(devPoints, secPoints, opsPoints), devPoints, secPoints, opsPoints)
    }
}
