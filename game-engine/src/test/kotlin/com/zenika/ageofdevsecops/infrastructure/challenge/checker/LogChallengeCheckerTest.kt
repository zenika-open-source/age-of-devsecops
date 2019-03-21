package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.zenika.ageofdevsecops.domain.challenge.Instance
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.SecurityGroup
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import kotlin.test.BeforeTest
import kotlin.test.Test

class LogChallengeCheckerTest {

    private val restTemplate = RestTemplate()

    private lateinit var challengeChecker: LogChallengeChecker

    private lateinit var mockServer: MockRestServiceServer
    private val instance = Instance("main", "url")
    private val player = Player("player1", instance, instance, instance, instance, SecurityGroup(""), SecurityGroup(""), SecurityGroup(""))
    private val playerLogUrl = "https://${player.main.url}/actuator/logfile"

    @BeforeTest
    fun setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
        mockServer.expect(MockRestRequestMatchers.requestTo("https://${player.main.url}/admin"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("SUCCESS", MediaType.TEXT_PLAIN))

        challengeChecker = LogChallengeChecker(restTemplate)
    }


    @Test
    fun `should return true if player removed password from log`() {

        mockServer.expect(MockRestRequestMatchers.requestTo(playerLogUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("admin logged", MediaType.TEXT_PLAIN))


        val challengeStatus = challengeChecker.check(player)
        assertk.assert(challengeStatus).isTrue()

    }

    @Test
    fun `should return false if player does not removed password from log`() {

        mockServer.expect(MockRestRequestMatchers.requestTo(playerLogUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("admin logged with password M33KTczYfsmu9CWk", MediaType.TEXT_PLAIN))


        val challengeStatus = challengeChecker.check(player)
        assertk.assert(challengeStatus).isFalse()

    }

    @Test
    fun `should return false if player app is not responding`() {

        mockServer.expect(MockRestRequestMatchers.requestTo(playerLogUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND))


        val challengeStatus = challengeChecker.check(player)
        assertk.assert(challengeStatus).isFalse()

    }
}