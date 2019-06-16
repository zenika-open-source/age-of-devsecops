package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.FlagStatuses
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.PlayerChallengesChecker
import com.zenika.ageofdevsecops.domain.challenge.PlayerChallengesReport
import java.util.*

class PlayerChallengesCheckerFakeImpl : PlayerChallengesChecker {

    private val random = Random()

//    private val challenges = listOf(
//            Challenge("Default Error Page", 1, 1, 0),
//            Challenge("Dependencies", 0, 2, 2),
//            Challenge("Uptime", 1, 1, 1),
//            Challenge("Spring Vulnerability Actuator", 1, 3, 0),
//            Challenge("Load Balancing", 0, 0, 5),
//            Challenge("Logs", 1, 2, 0),
//            Challenge("Sonar", 0, 0, 2),
//            Challenge("Default Error Page2", 1, 1, 0),
//            Challenge("Default Error Page3", 1, 1, 0),
//            Challenge("Default Error Page4", 1, 1, 0)
//    )

    override fun onboard(player: Player): PlayerChallengesReport {
        return PlayerChallengesReport(
//                challenges.map { ChallengeStatus(it, false) },
                emptyList(),
                FlagStatuses(0, 5, 0, 0, 0, 0)
        )
    }

    override fun check(player: Player): PlayerChallengesReport {
        return PlayerChallengesReport(
//                challenges.map { ChallengeStatus(it, random.nextBoolean()) },
                emptyList(),
                FlagStatuses(random.nextInt(6), 5, random.nextInt(11), random.nextInt(16),
                        random.nextInt(16), random.nextInt(16))
        )
    }
}
