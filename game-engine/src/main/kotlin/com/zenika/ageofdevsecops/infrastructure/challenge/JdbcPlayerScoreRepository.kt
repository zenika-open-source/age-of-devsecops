package com.zenika.ageofdevsecops.infrastructure.challenge

import com.zenika.ageofdevsecops.domain.challenge.PlayerScore
import com.zenika.ageofdevsecops.domain.challenge.PlayerScoreRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcPlayerScoreRepository(val jdbcTemplate: JdbcTemplate) : PlayerScoreRepository {


    override fun save(playerScore: PlayerScore) {
        val sql = "REPLACE INTO SCORES(playerId,score,progression,devPoint,secPoint,opsPoint) VALUES (?,?,?,?,?,?)"
        jdbcTemplate.update(sql, playerScore.playerId, playerScore.score, playerScore.progression, playerScore.devPoint, playerScore.secPoint, playerScore.opsPoint)
    }


    override fun findAll(): List<PlayerScore> {

        val sql = "SELECT playerId,score,progression,devPoint,secPoint,opsPoint FROM SCORES"

        return jdbcTemplate.query(sql) { rs, _ ->
            PlayerScore(
                    rs.getString("playerId"),
                    rs.getInt("score"),
                    rs.getInt("progression"),
                    rs.getInt("devPoint"),
                    rs.getInt("secPoint"),
                    rs.getInt("opsPoint"))
        }
    }
}
