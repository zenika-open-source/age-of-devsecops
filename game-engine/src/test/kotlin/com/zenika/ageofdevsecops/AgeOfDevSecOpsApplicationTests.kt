package com.zenika.ageofdevsecops

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.ec2.AmazonEC2
import com.zenika.ageofdevsecops.domain.challenge.Instance
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.SecurityGroup
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class AgeOfDevSecOpsApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Configuration
	class GameTestConfig {

		@Bean
		fun players(): List<Player> {
			return listOf(
                    Player("player1", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
                    Player("player2", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup(""))
			)
		}

		@Bean
		fun ec2(): AmazonEC2 {
			return mockk(relaxed = true)
		}

		@Bean
		fun credentialProvider(): AWSCredentialsProvider = AWSStaticCredentialsProvider(BasicAWSCredentials("accessKey", "secretKey"))
	}

}

