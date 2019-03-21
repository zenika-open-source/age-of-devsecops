package com.zenika.ageofdevsecops.application

import com.zenika.ageofdevsecops.domain.challenge.Scheduler

class GameRoundTrigger(
        private val gameEngine: GameEngine,
        scheduler: Scheduler
) {
    private var paused = true

    init {
        scheduler.setInterval(60_000, Runnable {
            if (!paused) {
                gameEngine.nextRound()
            }
        })
    }

    fun pause() {
        paused = true
    }

    fun resume() {
        paused = false
    }
}
