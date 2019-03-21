package com.zenika.ageofdevsecops.domain.player

interface PlayerInfoRepository {
    fun findByToken(token: String): PlayerInfo?
}
