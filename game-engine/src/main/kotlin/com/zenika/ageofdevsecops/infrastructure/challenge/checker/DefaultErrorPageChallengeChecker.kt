package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Component
class DefaultErrorPageChallengeChecker(private val restTemplate: RestTemplate, private val healthChecker: HealthChecker) : ChallengeChecker {

    private val defaultErrorTitle = "<h1>Whitelabel Error Page</h1>"

    override fun check(player: Player): Boolean {
        return try {
            val status = healthChecker.verify(player.main.url)
            if (status.status != "UP") {
                return false
            }

            val headers = HttpHeaders()
            headers.accept = listOf(MediaType.TEXT_HTML)
            val entity = HttpEntity<String>(headers)
            restTemplate.exchange<String>("https://${player.main.url}/some_non_exist_page", HttpMethod.GET, entity)

            false
        } catch (e: HttpClientErrorException.NotFound) {
            val errorBody = e.responseBodyAsString

            !errorBody.contains(defaultErrorTitle)
        } catch (e: Exception) {
            false
        }
    }

}