package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import assertk.assert
import assertk.assertions.isEqualTo
import com.zenika.ageofdevsecops.domain.challenge.FlagStatuses
import com.zenika.ageofdevsecops.domain.challenge.Instance
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.SecurityGroup
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate
import kotlin.test.BeforeTest
import kotlin.test.Test

class FlagsCheckerTest {

    private val restTemplate = RestTemplate()
    private val flagRepository = mockk<FlagRepository>(relaxed = true)

    private val savedFlags = listOf(
            Flag("FLAG1", 1, 0, 0),
            Flag("FLAG2", 1, 2, 0),
            Flag("SECRET_FLAG", 0, 0, 1)
    )

    private lateinit var flagChecker: FlagsChecker

    private lateinit var mockServer: MockRestServiceServer
    private val instance = Instance("main", "url")
    private val player = Player("player1", instance, instance, instance, instance, SecurityGroup(""), SecurityGroup(""), SecurityGroup(""))
    private val playerFlagsEndpoint = "https://${player.main.url}/flags"

    @BeforeTest
    fun setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
        every { flagRepository.findAll() } returns savedFlags
        flagChecker = FlagsChecker(flagRepository, restTemplate)
    }

    @Test
    fun `should check flags challenge status`() {
        mockServer.expect(requestTo(playerFlagsEndpoint))
                .andExpect(method(GET))
                .andRespond(withSuccess("[\"FLAG1\", \"SECRET_FLAG\"]", MediaType.APPLICATION_JSON))


        val flagStatuses = flagChecker.check(player)
        assert(flagStatuses).isEqualTo(FlagStatuses(1, 2, 1, 1, 0, 1))
    }

    @Test
    fun `should avoid duplicated player flags`() {

        mockServer.expect(requestTo(playerFlagsEndpoint))
                .andExpect(method(GET))
                .andRespond(withSuccess("[\"FLAG1\",\"FLAG1\"]", MediaType.APPLICATION_JSON))


        val flagStatuses = flagChecker.check(player)
        assert(flagStatuses).isEqualTo(FlagStatuses(1, 2, 0, 1, 0, 0))
    }

    @Test
    fun `should give the correct number of public flags (id not starting with "SECRET_")`() {
        val publicFlagsCount = flagChecker.publicFlagsCount()
        assert(publicFlagsCount).isEqualTo(2)
    }
}
