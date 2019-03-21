package com.zenika.ageofdevsecops.exposition

import com.google.common.eventbus.Subscribe
import com.zenika.ageofdevsecops.domain.challenge.PlayerScore
import com.zenika.ageofdevsecops.domain.challenge.event.PlayerScoresUpdated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/scores")
class ScoreController {

    var lastPlayerScores: Set<PlayerScore> = emptySet()

    @GetMapping
    fun getScores(): Set<PlayerScore> {
        return lastPlayerScores;
    }

    @Subscribe
    fun handle(playerScoresUpdated: PlayerScoresUpdated) {
        lastPlayerScores = playerScoresUpdated.scores
    }
}
