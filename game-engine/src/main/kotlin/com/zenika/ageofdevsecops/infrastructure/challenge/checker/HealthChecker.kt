package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Component
class HealthChecker(private val restTemplate: RestTemplate) {

    fun verify(ip: String): Status {
        return try {
            restTemplate.getForObject("https://$ip/actuator/health")!!
        } catch (e: Exception) {
            return Status("DOWN")
        }
    }

    data class Status(val status: String)
}