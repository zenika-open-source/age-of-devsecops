package com.zenika.ageofdevsecops

import com.zenika.ageofdevsecops.domain.challenge.Instance
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.PlayerChallengesChecker
import com.zenika.ageofdevsecops.domain.challenge.SecurityGroup
import com.zenika.ageofdevsecops.infrastructure.challenge.checker.PlayerChallengesCheckerFakeImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
@Profile("dev")
class GameConfigDev {

    @Bean
    @Primary
    fun playerChallengesChecker(): PlayerChallengesChecker = PlayerChallengesCheckerFakeImpl()

//    @Bean
//    @Primary
//    fun players(): List<Player> {
//        return listOf(
//                Player("ophore", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("aneitov", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("piwei", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("dakonus", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("akinez", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("zatruna", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("votis", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("xenvion", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("pucara", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("estea", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("dragano", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("carebos", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("obade", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("astriturn", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("azuno", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("tivilles", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("ogophus", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("thunides", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("kinziri", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("kaiphus", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("luuria", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("trioteru", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("strooter", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("tesade", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup("")),
//                Player("niborus", Instance("", ""), Instance("", ""), Instance("", ""), Instance("", ""), SecurityGroup(""), SecurityGroup(""), SecurityGroup(""))
//        )
//    }
}
