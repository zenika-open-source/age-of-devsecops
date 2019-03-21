package com.zenika.ageofdevsecops.infrastructure

import assertk.assert
import assertk.assertions.containsExactly
import com.zenika.ageofdevsecops.domain.challenge.PlayerScore
import com.zenika.ageofdevsecops.infrastructure.challenge.JdbcPlayerScoreRepository
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.Test


@RunWith(SpringRunner::class)
@JdbcTest
@AutoConfigureTestDatabase
@Import(JdbcPlayerScoreRepository::class)
@Sql(statements = ["SET DATABASE SQL SYNTAX MYS TRUE"])
class JdbcPlayerScoreRepositoryTest {

    @Autowired
    lateinit var playerScoreRepository: JdbcPlayerScoreRepository;

    @Test
    fun `should save player score if is not exist yet`() {

        val score = PlayerScore("player1", 1, 2, 1, 1, 1)
        playerScoreRepository.save(score)

        val allScores = playerScoreRepository.findAll()

        assert(allScores).containsExactly(score)
    }

    @Test
    fun `should replace player score if already exists`() {

        val score1 = PlayerScore("player1", 1, 2, 1, 1, 1)
        playerScoreRepository.save(score1)

        val score2 = PlayerScore("player1", 3, 1, 2, 1, 3)
        playerScoreRepository.save(score2)

        val allScores = playerScoreRepository.findAll()

        assert(allScores).containsExactly(score2)
    }
}
