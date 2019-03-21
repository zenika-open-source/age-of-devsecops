package com.zenika.ageofdevsecops.domain.challenge

data class FlagStatuses(
        val publicPresent: Int,
        val publicTotal: Int,
        val secretPresent: Int,
        val devPoint: Int,
        val secPoint: Int,
        val opsPoint: Int
)
