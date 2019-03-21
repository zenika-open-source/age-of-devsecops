package com.zenika.ageofdevsecops.application

import com.zenika.ageofdevsecops.domain.challenge.*
import com.zenika.ageofdevsecops.domain.challenge.event.DomainEventBus
import com.zenika.ageofdevsecops.domain.challenge.event.PlayerChallengeStatusesUpdated
import com.zenika.ageofdevsecops.domain.challenge.event.PlayerScoresUpdated
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test


class GameEngineTest {

    private val player1 = Player("player1", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup(""))
    private val player2 = Player("player2", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup(""))
    private val challengesChecker = mockk<PlayerChallengesChecker>(relaxed = true)
    private val reportGrader = mockk<ReportGrader>(relaxed = true)
    private val playerScoreRepository = mockk<PlayerScoreRepository>(relaxed = true)
    private val eventBus = mockk<DomainEventBus>(relaxed = true)
    private val challenge1 = Challenge("C1", 4, 4, 4)
    private val initReport = PlayerChallengesReport(
            listOf(ChallengeStatus(challenge1, false)),
            FlagStatuses(0, 10, 0, 0, 0, 0)
    )
    private val report1 = PlayerChallengesReport(
            listOf(ChallengeStatus(challenge1, true)),
            FlagStatuses(1, 10, 1, 2, 3, 1)
    )
    private val report2 = PlayerChallengesReport(
            listOf(ChallengeStatus(challenge1, false)),
            FlagStatuses(0, 10, 0, 0, 0, 0)
    )
    private val report1Grade = ReportGrade(4, 4, 4, 4)
    private val report2Grade = ReportGrade(0, 0, 0, 0)

    private fun fixtureGame() =
            GameEngine(listOf(player1, player2), challengesChecker, reportGrader, playerScoreRepository, eventBus)

    @BeforeTest
    fun before() {
        every { playerScoreRepository.findAll() } returns listOf(
                PlayerScore(player1.id, 0, 0, 0, 0, 0),
                PlayerScore(player2.id, 0, 0, 0, 0, 0)
        )
        every { challengesChecker.onboard(any()) } returns initReport
        every { challengesChecker.check(player1) } returns report1
        every { challengesChecker.check(player2) } returns report2
        every { reportGrader.grade(report1) } returns report1Grade
        every { reportGrader.grade(report2) } returns report2Grade
    }

    @Test
    fun `all players should start with score 0`() {
        fixtureGame()

        verify {
            eventBus.emit(PlayerScoresUpdated(setOf(
                    PlayerScore(player1.id, 0, 0, 0, 0, 0),
                    PlayerScore(player2.id, 0, 0, 0, 0, 0)
            )))
        }
    }

    @Test
    fun `should use report grades as score at round 1`() {
        val game = fixtureGame()

        game.nextRound()

        verify {
            eventBus.emit(PlayerScoresUpdated(setOf(
                    PlayerScore(player1.id, report1Grade.score, report1Grade.score, report1Grade.devPoint, report1Grade.secPoint, report1Grade.opsPoint),
                    PlayerScore(player2.id, report2Grade.score, report2Grade.score, report2Grade.devPoint, report2Grade.secPoint, report2Grade.opsPoint)
            )))
        }
    }

    @Test
    fun `should increment player scores at each round`() {
        val game = fixtureGame()

        game.nextRound()
        game.nextRound()
        game.nextRound()

        val player1PreviousScore = 4
        val player2PreviousScore = 0
        val previousScores = listOf(
                PlayerScore(player1.id, player1PreviousScore, 4, 4, 4, 4),
                PlayerScore(player2.id, player2PreviousScore, 0, 0, 0, 0)
        )
        every { playerScoreRepository.findAll() } returns previousScores

        val round2Report1 = PlayerChallengesReport(
                listOf(ChallengeStatus(challenge1, true)),
                FlagStatuses(0, 10, 0, 0, 0, 0)
        )
        val round2Report2 = PlayerChallengesReport(
                listOf(ChallengeStatus(challenge1, true)),
                FlagStatuses(0, 10, 0, 0, 0, 0)
        )
        every { challengesChecker.check(player1) } returns round2Report1
        every { challengesChecker.check(player2) } returns round2Report2
        val round2Report1XP = ReportGrade(4, 4, 4, 4)
        val round2Report2XP = ReportGrade(4, 4, 4, 4)
        every { reportGrader.grade(round2Report1) } returns round2Report1XP
        every { reportGrader.grade(round2Report2) } returns round2Report2XP

        game.nextRound()

        verify {
            eventBus.emit(PlayerScoresUpdated(setOf(
                    PlayerScore(player1.id, player1PreviousScore + round2Report1XP.score, round2Report1XP.score, round2Report1XP.devPoint, round2Report1XP.secPoint, round2Report1XP.opsPoint),
                    PlayerScore(player2.id, player2PreviousScore + round2Report2XP.score, round2Report2XP.score, round2Report2XP.devPoint, round2Report2XP.secPoint, round2Report2XP.opsPoint)
            )))
        }

    }

    @Test
    fun `should add 0 when xp is negative (poison flag)`() {
        val report1XP = ReportGrade(Int.MIN_VALUE + 3, Int.MIN_VALUE + 5, Int.MIN_VALUE + 3, Int.MIN_VALUE + 10)
        val report2XP = ReportGrade(10, 10, 10, 10)
        every { reportGrader.grade(report1) } returns report1XP
        every { reportGrader.grade(report2) } returns report2XP
        val game = fixtureGame()

        game.nextRound()

        verify {
            eventBus.emit(PlayerScoresUpdated(setOf(
                    PlayerScore(player1.id, 0, 0, 0, 0, 0),
                    PlayerScore(player2.id, report2XP.score, report2XP.score, report2XP.devPoint, report2XP.secPoint, report2XP.opsPoint)
            )))
        }
    }

    @Test
    fun `should notify about resolved challenges`() {
        val game = fixtureGame()

        game.nextRound()

        verify {
            eventBus.emit(PlayerChallengeStatusesUpdated(setOf(
                    PlayerChallengeStatuses(player1.id, report1Grade.score, report1.challengeStatuses, report1.flagStatuses),
                    PlayerChallengeStatuses(player2.id, report2Grade.score, report2.challengeStatuses, report2.flagStatuses)
            )))
        }
    }

    @Test
    fun `should restore scores from previous runs if any`() {
        val previousScores = listOf(
                PlayerScore(player1.id, 12, 5, 5, 6, 7),
                PlayerScore(player2.id, 10, 4, 4, 6, 7)
        )
        every { playerScoreRepository.findAll() } returns previousScores
        fixtureGame()

        verify {
            eventBus.emit(PlayerScoresUpdated(previousScores.toSet()))
        }
        verify {
            eventBus.emit(PlayerChallengeStatusesUpdated(setOf(
                    PlayerChallengeStatuses(player1.id, 12, initReport.challengeStatuses, initReport.flagStatuses),
                    PlayerChallengeStatuses(player2.id, 10, initReport.challengeStatuses, initReport.flagStatuses)
            )))
        }
    }
}
