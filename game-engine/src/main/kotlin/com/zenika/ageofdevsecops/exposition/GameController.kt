package com.zenika.ageofdevsecops.exposition

import com.zenika.ageofdevsecops.application.GameRoundTrigger
import com.zenika.ageofdevsecops.application.InstancesAccessManager
import com.zenika.ageofdevsecops.application.PlayerManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class GameController(
        private val roundTrigger: GameRoundTrigger,
        private val playerManager: PlayerManager,
        private val instancesAccessManager: InstancesAccessManager
) {
    @PostMapping(path = ["/game/pause"])
    fun pauseGame() {
        roundTrigger.pause()
//        instancesAccessManager.denySshAccess()
    }

    @PostMapping(path = ["/game/resume"])
    fun resumeGame() {
//        instancesAccessManager.permitSshAccess()
        roundTrigger.resume()
    }

//    @PostMapping(path = ["/onboarding/pause"])
//    fun pauseOnboarding() {
//        playerManager.disableOnboarding()
//    }
//
//    @PostMapping(path = ["/onboarding/resume"])
//    fun resumeOnboarding() {
//        playerManager.enableOnboarding()
//    }
}
