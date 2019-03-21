package com.zenika.ageofdevsecops.exposition

import com.google.common.eventbus.Subscribe
import com.zenika.ageofdevsecops.domain.challenge.PlayerChallengeStatuses
import com.zenika.ageofdevsecops.domain.challenge.event.PlayerChallengeStatusesUpdated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/challenges/status")
class ChallengesStatusController {

    private var lastChallengeStatuses: Set<PlayerChallengeStatuses> = emptySet()

    @GetMapping
    fun fetchChallengesStatus(): Set<PlayerChallengeStatuses> {
        return lastChallengeStatuses
    }

    @Subscribe
    fun handle(playerChallengeStatusesUpdated: PlayerChallengeStatusesUpdated) {
        lastChallengeStatuses = playerChallengeStatusesUpdated.challengeStatuses
    }
}
