package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForObject
import java.util.concurrent.TimeUnit

//@Component
class LogChallengeChecker(private val restTemplate: RestTemplate) : ChallengeChecker {

    private val expectedResult = "logged"
    private val headers = HttpHeaders()
    private val pass = "M33KTczYfsmu9CWk"
    private val logger = LoggerFactory.getLogger(LogChallengeChecker::class.java)

    init {
        headers.setBasicAuth("admin", pass)
    }

    override fun check(player: Player): Boolean {
        return try {
            val entity = HttpEntity<String>(headers)
            restTemplate.exchange<String>("https://${player.main.url}/admin", HttpMethod.GET, entity)

            TimeUnit.SECONDS.sleep(1)

            val logFile = restTemplate.getForObject<String>("https://${player.main.url}/actuator/logfile")!!
            return logFile.contains(expectedResult) && !logFile.contains(pass)
        } catch (e: Throwable) {
            logger.warn("Error log file for player : {}", player.id, e)
            false
        }
    }

}
