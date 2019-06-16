package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.FlagStatuses
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.PlayerChallengesChecker
import com.zenika.ageofdevsecops.domain.challenge.PlayerChallengesReport
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext

class PlayerChallengesCheckerImpl(
        private val context: ApplicationContext,
        private val flagsChecker: FlagsChecker
) : PlayerChallengesChecker {

//    private val checkerByChallenge = mapOf(
//            Challenge("Uptime", 1, 1, 1) to HealthChallengeChecker::class.java,
//            Challenge("Spring Actuator", 1, 3, 0) to HeapDumpChallengeChecker::class.java,
//            Challenge("Dependencies", 0, 2, 2) to DependencyChallengeChecker::class.java,
//            Challenge("Default Error Page", 1, 1, 0) to DefaultErrorPageChallengeChecker::class.java,
//            Challenge("Load Balancing", 0, 0, 5) to LoadBalancerChallengeChecker::class.java,
//            Challenge("Sonar", 0, 0, 2) to SonarChallengeChecker::class.java,
//            Challenge("Quality", 4, 0, 0) to QualityCodeChallengeChecker::class.java,
//            Challenge("Logs", 1, 2, 0) to LogChallengeChecker::class.java,
//            Challenge("Secure transmissions", 0, 2, 3) to SSLCertificateChecker::class.java
//    )

    private val logger = LoggerFactory.getLogger(PlayerChallengesCheckerImpl::class.java)

    override fun onboard(player: Player): PlayerChallengesReport {
        return PlayerChallengesReport(
//                checkerByChallenge.keys.map { ChallengeStatus(it, false) },
                emptyList(),
                FlagStatuses(0, flagsChecker.publicFlagsCount(), 0, 0, 0, 0)
        )
    }

    override fun check(player: Player): PlayerChallengesReport {
//        val challengeStatuses = Flux
//                .mergeSequential(
//                        checkerByChallenge
//                                .map {
//                                    CompletableFuture.supplyAsync {
//                                        val checker = context.getBean(it.value)
//                                        val completed = checker.check(player)
//                                        logger.info("Player {} completed challenge {}: {}", player.id, it.key.name, completed)
//                                        ChallengeStatus(it.key, completed)
//                                    }
//                                }
//                                .map { Mono.fromFuture { it } }
//                )
//                .collectList()
//                .block()!!

        val flagStatuses = flagsChecker.check(player)
//        logger.info("Player {} completed public flags: {}, hidden flags: {}",
//                player.id, flagStatuses.publicPresent, flagStatuses.secretPresent)

//        return PlayerChallengesReport(challengeStatuses, flagStatuses)
        return PlayerChallengesReport(emptyList(), flagStatuses)
    }
}
