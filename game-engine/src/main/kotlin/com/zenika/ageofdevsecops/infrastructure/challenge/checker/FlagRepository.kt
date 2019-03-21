package com.zenika.ageofdevsecops.infrastructure.challenge.checker


interface FlagRepository {
    fun findAll(): List<Flag>
}
