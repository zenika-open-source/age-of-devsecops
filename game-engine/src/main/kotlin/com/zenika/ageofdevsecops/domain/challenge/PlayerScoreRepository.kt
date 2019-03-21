package com.zenika.ageofdevsecops.domain.challenge

interface PlayerScoreRepository {

    fun save(playerScore: PlayerScore)
    fun findAll(): List<PlayerScore>

}
