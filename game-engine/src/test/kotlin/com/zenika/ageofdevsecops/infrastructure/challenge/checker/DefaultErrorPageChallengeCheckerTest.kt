package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.zenika.ageofdevsecops.domain.challenge.Instance
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.SecurityGroup
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultErrorPageChallengeCheckerTest {

    private val restTemplate = RestTemplate()

    private lateinit var challengeChecker: DefaultErrorPageChallengeChecker

    private lateinit var mockServer: MockRestServiceServer
    private val healthChecker = mockk<HealthChecker>(relaxed = true)
    private val instance = Instance("main", "url")
    private val player = Player("player1", instance, instance, instance, instance, SecurityGroup(""), SecurityGroup(""), SecurityGroup(""))
    private val playerFakeUrl = "https://${player.main.url}/some_non_exist_page"

    @BeforeTest
    fun setUp() {
        every {  healthChecker.verify(instance.url) } returns HealthChecker.Status("UP")

        mockServer = MockRestServiceServer.createServer(restTemplate)
        challengeChecker = DefaultErrorPageChallengeChecker(restTemplate, healthChecker)
    }


    @Test
    fun `should return true if player modified error page`() {
        mockServer.expect(MockRestRequestMatchers.requestTo(playerFakeUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND).body("ERROR"))


        val challengeStatus = challengeChecker.check(player)
        assertk.assert(challengeStatus).isTrue()

    }

    @Test
    fun `should return false if player does not modified error page`() {
        mockServer.expect(MockRestRequestMatchers.requestTo(playerFakeUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND).body("<h1>Whitelabel Error Page</h1>"))


        val challengeStatus = challengeChecker.check(player)
        assertk.assert(challengeStatus).isFalse()

    }

    @Test
    fun `should return false if player app is not responding`() {
        every {  healthChecker.verify(instance.url) } returns HealthChecker.Status("DOWN")

        val challengeStatus = challengeChecker.check(player)
        assertk.assert(challengeStatus).isFalse()

    }

    @Test
    fun `should return false if url respond`() {
        mockServer.expect(MockRestRequestMatchers.requestTo(playerFakeUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess())

        val challengeStatus = challengeChecker.check(player)
        assertk.assert(challengeStatus).isFalse()

    }
}