package com.zenika.ageofdevsecops.infrastructure.player

import com.zenika.ageofdevsecops.domain.player.PlayerInfo
import com.zenika.ageofdevsecops.domain.player.PlayerInfoRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcPlayerInfoRepository(val jdbcTemplate: JdbcTemplate) : PlayerInfoRepository {


    override fun findByToken(token: String): PlayerInfo? {
        val sql = """
            SELECT id, token, cloud9URL, awsAccountId, login, password, sshKey
            FROM PLAYERS_INFO
            WHERE token = ?
        """

        val result = jdbcTemplate.query(sql, arrayOf(token)) { rs, _ ->
            PlayerInfo(
                    rs.getString("id"),
                    rs.getString("token"),
                    rs.getString("cloud9URL"),
                    rs.getString("awsAccountId"),
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getString("sshKey")
            )
        }

        return if (result.size != 1) null else result[0]
    }
}
