package com.zenika.ageofdevsecops.infrastructure.challenge

import com.zenika.ageofdevsecops.domain.challenge.Scheduler
import org.slf4j.LoggerFactory
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Component


@Component
class SpringScheduler : Scheduler {

    private val taskScheduler = ThreadPoolTaskScheduler()
    private val logger = LoggerFactory.getLogger(SpringScheduler::class.java)

    init {
        taskScheduler.initialize()
    }

    override fun setInterval(intervalMillis: Long, task: Runnable) {
        logger.info("Trigger task")
        taskScheduler.scheduleWithFixedDelay(task, intervalMillis)
        logger.info("End task")
    }
}
