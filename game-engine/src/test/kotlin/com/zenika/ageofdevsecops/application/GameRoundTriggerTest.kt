package com.zenika.ageofdevsecops.application

import com.zenika.ageofdevsecops.domain.challenge.Scheduler
import io.mockk.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class GameRoundTriggerTest {

    private val gameEngine = mockk<GameEngine>(relaxed = true)
    private val scheduler = mockk<Scheduler>(relaxed = true)
    private lateinit var taskCapture: CapturingSlot<Runnable>
    private lateinit var gameRoundTrigger: GameRoundTrigger

    @BeforeTest
    fun before() {
        taskCapture = slot()
        every { scheduler.setInterval(any(), task = capture(taskCapture)) } answers {}
        gameRoundTrigger = GameRoundTrigger(gameEngine, scheduler)
    }

    @Test
    fun `should schedule the round trigger every 60s`() {
        verify { scheduler.setInterval(60_000, any()) }
    }

    @Test
    fun `should trigger game round at interval when game is playing`() {
        val task = taskCapture.captured

        gameRoundTrigger.resume()
        task.run()
        task.run()

        verify(exactly = 2) { gameEngine.nextRound() }
    }

    @Test
    fun `should not trigger game round when game is paused`() {
        val task = taskCapture.captured

        gameRoundTrigger.pause()
        task.run()

        verify(exactly = 0) { gameEngine.nextRound() }
    }

    @Test
    fun `should trigger game round again when game is resumed`() {
        val task = taskCapture.captured

        gameRoundTrigger.pause()
        task.run()

        gameRoundTrigger.resume()
        task.run()
        task.run()

        verify(exactly = 2) { gameEngine.nextRound() }
    }

    @Test
    fun `should be paused by default`() {
        val task = taskCapture.captured

        task.run()

        verify(exactly = 0) { gameEngine.nextRound() }
    }
}
