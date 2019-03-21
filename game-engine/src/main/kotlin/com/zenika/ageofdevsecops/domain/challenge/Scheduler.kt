package com.zenika.ageofdevsecops.domain.challenge

interface Scheduler {
    fun setInterval(intervalMillis: Long, task: Runnable)
}
