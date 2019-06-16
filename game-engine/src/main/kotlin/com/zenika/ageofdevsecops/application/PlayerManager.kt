package com.zenika.ageofdevsecops.application

import com.zenika.ageofdevsecops.domain.player.PlayerInfo
import com.zenika.ageofdevsecops.domain.player.PlayerInfoRepository

class PlayerManager(
        private val playerInfoRepository: PlayerInfoRepository
) {
//    private var onboardingEnabled = false

    fun onboardPlayer(token: String): PlayerInfo? {
//        return if (onboardingEnabled) {
        return playerInfoRepository.findByToken(token)
//        } else {
//            null
//        }
        // TODO somewhere here we should add a mechanism to make the player join the game at this point only.
    }

    fun disableOnboarding() {
//        onboardingEnabled = false
    }

    fun enableOnboarding() {
//        onboardingEnabled = true
    }
}
