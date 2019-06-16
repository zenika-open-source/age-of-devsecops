package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

//@Component
class DependencyChallengeChecker(private val restTemplate: RestTemplate) : ChallengeChecker {

    private val body = "[{ \"op\" : \"replace\", \"path\" : \"T(org.springframework.util.StreamUtils).copy(T(java.lang.Runtime).getRuntime().exec(\\\"echo toto\\\").getInputStream(), T(org.springframework.web.context.request.RequestContextHolder).currentRequestAttributes().getResponse().getOutputStream()).x\" }]"
    private val expectedResult = "toto"

    override fun check(player: Player): Boolean {
        return try {
            val headers = HttpHeaders()
            headers.set("Content-type", "application/json-patch+json")

            val httpEntity: HttpEntity<String> = HttpEntity(body, headers)
            val result = restTemplate
                    .exchange<String>(
                            "https://${player.main.url}/entities/1",
                            HttpMethod.PATCH,
                            httpEntity
                    )

            !result.body!!.contains(expectedResult)
        } catch (e: HttpClientErrorException.BadRequest) {
            true
        } catch (e: Exception) {
            false
        }
    }

}
