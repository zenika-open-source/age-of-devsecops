package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

//@Component
class HeapDumpChallengeChecker(private val restTemplate: RestTemplate, private val healthChecker: HealthChecker) : ChallengeChecker {

    override fun check(player: Player): Boolean {
        return try {
            val status = healthChecker.verify(player.main.url)

            if (status.status != "UP") {
                return false
            }

            restTemplate.headForHeaders("https://${player.main.url}/actuator/heapdump")

            false
        } catch (e: Exception) {
            true
        }
    }

}
