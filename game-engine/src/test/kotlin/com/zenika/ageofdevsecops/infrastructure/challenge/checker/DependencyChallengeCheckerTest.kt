package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.zenika.ageofdevsecops.domain.challenge.Instance
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.SecurityGroup
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import kotlin.test.BeforeTest
import kotlin.test.Test

class DependencyChallengeCheckerTest {

    private val restTemplate = RestTemplate()

    private lateinit var dependencyChallengeChecker: DependencyChallengeChecker

    private lateinit var mockServer: MockRestServiceServer
    private val instance = Instance("main", "url")
    private val player = Player("player1", instance, instance, instance, instance, SecurityGroup(""), SecurityGroup(""), SecurityGroup(""))
    private val playerStrutsUrl = "https://${player.main.url}/entities/1"

    @BeforeTest
    fun setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
        dependencyChallengeChecker = DependencyChallengeChecker(restTemplate)
    }


    @Test
    fun `should return true if player has fix spring data rest bug`() {

        mockServer.expect(MockRestRequestMatchers.requestTo(playerStrutsUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PATCH))
                .andRespond(MockRestResponseCreators.withSuccess("your app is fully secured", MediaType.TEXT_PLAIN))


        val challengeStatus = dependencyChallengeChecker.check(player)
        assertk.assert(challengeStatus).isTrue()

    }

    @Test
    fun `should return false if player hasn't fix spring data rest bug`() {

        mockServer.expect(MockRestRequestMatchers.requestTo(playerStrutsUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PATCH))
                .andRespond(MockRestResponseCreators.withSuccess("toto\n abc", MediaType.TEXT_PLAIN))


        val challengeStatus = dependencyChallengeChecker.check(player)
        assertk.assert(challengeStatus).isFalse()

    }

    @Test
    fun `should return false if player app is not responding`() {

        mockServer.expect(MockRestRequestMatchers.requestTo(playerStrutsUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PATCH))
                .andRespond(MockRestResponseCreators.withStatus(NOT_FOUND))


        val challengeStatus = dependencyChallengeChecker.check(player)
        assertk.assert(challengeStatus).isFalse()

    }
}
