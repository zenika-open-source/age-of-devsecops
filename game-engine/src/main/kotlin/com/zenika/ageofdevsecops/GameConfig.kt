package com.zenika.ageofdevsecops

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder
import com.google.common.eventbus.EventBus
import com.zenika.ageofdevsecops.application.FlagManager
import com.zenika.ageofdevsecops.application.GameEngine
import com.zenika.ageofdevsecops.application.GameRoundTrigger
import com.zenika.ageofdevsecops.application.PlayerManager
import com.zenika.ageofdevsecops.domain.challenge.*
import com.zenika.ageofdevsecops.domain.challenge.event.DomainEventBus
import com.zenika.ageofdevsecops.domain.flagsubmission.FlagSubmissionRepository
import com.zenika.ageofdevsecops.domain.player.PlayerInfoRepository
import com.zenika.ageofdevsecops.exposition.ChallengesStatusController
import com.zenika.ageofdevsecops.exposition.ScoreController
import com.zenika.ageofdevsecops.infrastructure.GuavaEventBus
import com.zenika.ageofdevsecops.infrastructure.challenge.checker.FlagRepository
import com.zenika.ageofdevsecops.infrastructure.challenge.checker.FlagsChecker
import com.zenika.ageofdevsecops.infrastructure.challenge.checker.PlayerChallengesCheckerImpl
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.client.RestTemplate
import java.security.cert.CertificateException
import java.security.cert.CertificateExpiredException
import java.security.cert.X509Certificate
import java.time.Duration
import java.util.*


@Configuration
@EnableWebFluxSecurity
class GameConfig {

    @Bean
    fun gameEngine(players: List<Player>, playerChallengesChecker: PlayerChallengesChecker, reportGrader: ReportGrader,
                   playerScoreRepository: PlayerScoreRepository, eventBus: DomainEventBus): GameEngine =
            GameEngine(players, playerChallengesChecker, reportGrader, playerScoreRepository, eventBus)

    @Bean
    fun playerManager(playerInfoRepository: PlayerInfoRepository): PlayerManager = PlayerManager(playerInfoRepository)

    @Bean
    fun flagManager(playerInfoRepository: PlayerInfoRepository, flagRepository: FlagRepository,
                    flagSubmissionRepository: FlagSubmissionRepository): FlagManager =
            FlagManager(playerInfoRepository, flagRepository, flagSubmissionRepository)

    @Bean
    fun reportGrader() = ReportGrader()

    @Bean
    fun playerChallengesChecker(context: ApplicationContext, flagsChecker: FlagsChecker): PlayerChallengesChecker =
            PlayerChallengesCheckerImpl(context, flagsChecker)

    @Bean
    fun eventBus(playerScoreRepository: PlayerScoreRepository,
                 scoreController: ScoreController,
                 challengesStatusController: ChallengesStatusController
    ): GuavaEventBus {
        val eventBus = EventBus()
        eventBus.register(scoreController)
        eventBus.register(challengesStatusController)
        return GuavaEventBus(eventBus)
    }

    @Bean
    fun roundTrigger(gameEngine: GameEngine, scheduler: Scheduler) =
            GameRoundTrigger(gameEngine, scheduler)

    @Bean
    fun restTemplate(): RestTemplate {
        val acceptingTrustStrategy = object : TrustStrategy {
            @Throws(CertificateException::class)
            override fun isTrusted(x509Certificates: Array<X509Certificate>, s: String): Boolean {
                val certificate = x509Certificates[0]
                if (certificate.notAfter.before(Date())) {
                    throw CertificateExpiredException()
                }
                return true
            }
        }

        val requestFactory = HttpComponentsClientHttpRequestFactory()
        val builder = SSLContextBuilder()
        builder.loadTrustMaterial(null, acceptingTrustStrategy)
        val sslsf = SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE)
        val httpClient = HttpClients
                .custom()
                .setSSLSocketFactory(sslsf)
                .build()
        requestFactory.httpClient = httpClient

        return RestTemplateBuilder()
                .requestFactory { requestFactory }
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build()!!
    }

    @Bean
    fun credentialProvider(): AWSCredentialsProvider = EnvironmentVariableCredentialsProvider()

//    @Bean
//    fun players(ec2: AmazonEC2): List<Player> {
//        val instancesFinder = PlayersFinder(ec2)
//        instancesFinder.setUp()
//        return instancesFinder.getPlayers()
//    }

    @Bean
    fun players(): List<Player> {
        val dummyInstance = Instance("", "")
        val dummySecurityGroup = SecurityGroup("")
        return listOf(
                Player("pucara", dummyInstance, dummyInstance, dummyInstance, dummyInstance, dummySecurityGroup, dummySecurityGroup, dummySecurityGroup),
                Player("dragano", dummyInstance, dummyInstance, dummyInstance, dummyInstance, dummySecurityGroup, dummySecurityGroup, dummySecurityGroup),
                Player("luuria", dummyInstance, dummyInstance, dummyInstance, dummyInstance, dummySecurityGroup, dummySecurityGroup, dummySecurityGroup),
                Player("thunides", dummyInstance, dummyInstance, dummyInstance, dummyInstance, dummySecurityGroup, dummySecurityGroup, dummySecurityGroup),
                Player("ophore", dummyInstance, dummyInstance, dummyInstance, dummyInstance, dummySecurityGroup, dummySecurityGroup, dummySecurityGroup)
        )
    }

    @Bean
    fun ec2(@Value("\${amazon.ec2.region}") amazonRegion: String): AmazonEC2 {
        return AmazonEC2ClientBuilder.standard()
                .withRegion(amazonRegion)
                .withCredentials(credentialProvider())
                .build()
    }

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.authorizeExchange()
                .pathMatchers("/api/scores/**", "/api/challenges/**", "/players/**", "/css/**", "/images/**", "/fonts/**").permitAll()
                .anyExchange().authenticated()
                .and().httpBasic()
                .and().csrf().disable()
                .build()
    }
}
