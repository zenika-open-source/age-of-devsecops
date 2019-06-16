package com.zenika.ageofdevsecops.application

import com.zenika.ageofdevsecops.domain.challenge.*
import com.zenika.ageofdevsecops.domain.challenge.event.DomainEventBus
import com.zenika.ageofdevsecops.domain.challenge.event.PlayerChallengeStatusesUpdated
import com.zenika.ageofdevsecops.domain.challenge.event.PlayerScoresUpdated
import java.util.Collections.synchronizedSet
import java.util.concurrent.CompletableFuture

class GameEngine(
        private val players: List<Player>,
        private val playerChallengesChecker: PlayerChallengesChecker,
        private val reportGrader: ReportGrader,
        private val playerScoreRepository: PlayerScoreRepository,
        private val domainEventBus: DomainEventBus
) {

    init {
        val previousScores = scoresFromPreviousRounds()
        val playerScores = emptySet<PlayerScore>().toMutableSet()
//        val playerChallengeStatuses = emptySet<PlayerChallengeStatuses>().toMutableSet()
        players.forEach {
            val initialReport = playerChallengesChecker.onboard(it)
            val reportGrade = reportGrader.grade(initialReport)
            if (previousScores.containsKey(it)) {
                playerScores.add(previousScores[it]!!)
//                playerChallengeStatuses.add(PlayerChallengeStatuses(it.id, previousScores[it]!!.score,
//                        initialReport.challengeStatuses, initialReport.flagStatuses))
            } else {
                playerScores.add(PlayerScore(it.id, reportGrade.score, reportGrade.score, reportGrade.devPoint, reportGrade.secPoint, reportGrade.opsPoint))
//                playerChallengeStatuses.add(PlayerChallengeStatuses(it.id, reportGrade.score,
//                        initialReport.challengeStatuses, initialReport.flagStatuses))
            }
        }
        emitScores(playerScores)
//        emitChallengeStatuses(playerChallengeStatuses)
    }

    fun nextRound() {
        val previousScores = scoresFromPreviousRounds()
        val playerScores = synchronizedSet(emptySet<PlayerScore>().toMutableSet())
//        val playerChallengeStatuses = synchronizedSet(emptySet<PlayerChallengeStatuses>().toMutableSet())
        val futures = players.map {
            CompletableFuture.supplyAsync {
                val report = playerChallengesChecker.check(it)
                val reportGrade = reportGrader.grade(report)
                val roundScore = Math.max(reportGrade.score, 0)
                val totalScore = previousScores[it]!!.score + roundScore
                playerScores.add(PlayerScore(it.id, totalScore, roundScore, Math.max(reportGrade.devPoint, 0),
                        Math.max(reportGrade.secPoint, 0), Math.max(reportGrade.opsPoint, 0)))
//                playerChallengeStatuses.add(PlayerChallengeStatuses(it.id, totalScore,
//                        report.challengeStatuses, report.flagStatuses))
            }
        }
        CompletableFuture.allOf(*futures.toTypedArray()).join()
        emitScores(playerScores)
//        emitChallengeStatuses(playerChallengeStatuses)
    }

    private fun scoresFromPreviousRounds(): Map<Player, PlayerScore> {
        val previousScores = playerScoreRepository.findAll()
        return players.mapNotNull { player ->
            previousScores
                    .find { it.playerId == player.id }
                    ?.let { player to it }
        }.toMap()
    }

    private fun emitScores(scores: Set<PlayerScore>) {
        scores.forEach(playerScoreRepository::save)
        domainEventBus.emit(PlayerScoresUpdated(scores))
    }

    private fun emitChallengeStatuses(statuses: Set<PlayerChallengeStatuses>) {
        domainEventBus.emit(PlayerChallengeStatusesUpdated(statuses))
    }
}
