package com.zenika.ageofdevsecops.infrastructure

import com.zenika.ageofdevsecops.domain.flagsubmission.FlagSubmission
import com.zenika.ageofdevsecops.domain.flagsubmission.FlagSubmissionRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcFlagSubmissionRepository(val jdbcTemplate: JdbcTemplate) : FlagSubmissionRepository {

    override fun findByPlayerAndFlag(playerId: String, flag: String): FlagSubmission? {
        return try {
            val sql = "SELECT playerId,flag FROM FLAG_SUBMISSIONS WHERE playerId = ? AND flag = ?"
            jdbcTemplate.queryForObject(sql, RowMapper { rs, _ ->
                FlagSubmission(
                        rs.getString("playerId"),
                        rs.getString("flag")
                )
            }, playerId, flag)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun findAllByPlayer(playerId: String): List<FlagSubmission> {
        val sql = "SELECT playerId,flag FROM FLAG_SUBMISSIONS WHERE playerId = ?"
        return jdbcTemplate.query(sql, RowMapper { rs, _ ->
            FlagSubmission(
                    rs.getString("playerId"),
                    rs.getString("flag")
            )
        }, playerId)
    }

    override fun save(flagSubmission: FlagSubmission) {
        val sql = "REPLACE INTO FLAG_SUBMISSIONS(playerId, flag) VALUES (?,?)"
        jdbcTemplate.update(sql, flagSubmission.playerId, flagSubmission.flag)
    }
}
