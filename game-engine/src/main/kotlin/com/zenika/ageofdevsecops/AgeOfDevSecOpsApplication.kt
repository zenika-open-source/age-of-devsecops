package com.zenika.ageofdevsecops

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class AgeOfDevSecOpsApplication

fun main(args: Array<String>) {
    runApplication<AgeOfDevSecOpsApplication>(*args)
}

