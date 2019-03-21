package com.zenika.ageofdevsecops.domain.challenge

data class PlayerScore(val playerId: String, val score: Int, val progression: Int,
                       val devPoint: Int, val secPoint: Int, val opsPoint: Int)
