package com.zenika.ageofdevsecops.domain

import assertk.assert
import assertk.assertions.isEqualTo
import com.zenika.ageofdevsecops.domain.challenge.*
import kotlin.test.Test

class ReportGraderTest {

    @Test
    fun `the report grade is the smallest of Dev, Sec and Ops points`() {
        val reportGrader = ReportGrader()
        val report = PlayerChallengesReport(
                listOf(
                        ChallengeStatus(Challenge("C1", 1, 1, 0), true),
                        ChallengeStatus(Challenge("C2", 2, 1, 1), true)
                ),
                FlagStatuses(1, 5, 1, 2, 0, 0)
        )

        val grade = reportGrader.grade(report)

        // Min(1+2+2, 1+1+0, 0+1+0) = 1
        assert(grade).isEqualTo(ReportGrade(1, 5, 2, 1))
    }

    @Test
    fun `only completed challenges count in the report grade`() {
        val reportGrader = ReportGrader()
        val report = PlayerChallengesReport(
                listOf(
                        ChallengeStatus(Challenge("C1", 1, 1, 0), true),
                        ChallengeStatus(Challenge("C2", 2, 1, 1), false)
                ),
                FlagStatuses(1, 2, 0, 1, 2, 3)
        )

        val grade = reportGrader.grade(report)

        // Min(1+1, 1+2, 0+3) = 2
        assert(grade).isEqualTo(ReportGrade(2, 2, 3, 3))
    }
}
