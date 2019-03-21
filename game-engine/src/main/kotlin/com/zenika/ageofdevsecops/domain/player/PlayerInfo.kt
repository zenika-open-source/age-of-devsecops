package com.zenika.ageofdevsecops.domain.player

data class PlayerInfo(
        val id: String,
        val token: String,
        val cloud9URL: String,
        val awsAccountId: String,
        val login: String,
        val password: String,
        val sshKey: String
)
