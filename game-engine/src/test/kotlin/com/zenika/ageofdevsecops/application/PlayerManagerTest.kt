package com.zenika.ageofdevsecops.application

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.zenika.ageofdevsecops.domain.player.PlayerInfo
import com.zenika.ageofdevsecops.domain.player.PlayerInfoRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.BeforeTest
import kotlin.test.Test

class PlayerManagerTest {

    private val token = "some_token"
    private val playerInfo = PlayerInfo("id", token, "url", "accountId", "login", "pass", "ssh-key")
    private val playerInfoRepository = mockk<PlayerInfoRepository>(relaxed = true)
    private lateinit var playerManager: PlayerManager

    @BeforeTest
    fun before() {
        every { playerInfoRepository.findByToken(token) } returns playerInfo
        playerManager = PlayerManager(playerInfoRepository)
    }

    @Test
    fun `should retrieve player info when onboarding is enabled`() {
        playerManager.enableOnboarding()

        val actual = playerManager.onboardPlayer(token)

        assert(actual).isEqualTo(playerInfo)
    }

    @Test
    fun `should not retrieve player info when onboarding is disabled`() {
        playerManager.disableOnboarding()

        val actual = playerManager.onboardPlayer(token)

        assert(actual).isNull()
    }

    @Test
    fun `onboarding should be disabled by default`() {
        val actual = playerManager.onboardPlayer(token)

        assert(actual).isNull()
    }
}
