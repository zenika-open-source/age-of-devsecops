package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.model.ModifyInstanceAttributeRequest
import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

//@Component
class LoadBalancerChallengeChecker(
        private val ec2: AmazonEC2,
        private val healthChecker: HealthChecker
) : ChallengeChecker {

    override fun check(player: Player): Boolean {
        TimeUnit.SECONDS.sleep(5)
        val disableNetworkRequest = ModifyInstanceAttributeRequest()
                .withInstanceId(player.node1.id)
                .withGroups(player.closedSecurityGroup.id, player.sshSecurityGroup.id)
        ec2.modifyInstanceAttribute(disableNetworkRequest)

        return try {
            TimeUnit.SECONDS.sleep(5)
            val firstStatus = healthChecker.verify(player.main.url)
            val secondStatus = healthChecker.verify(player.main.url)

            firstStatus.status == "UP" && secondStatus.status == "UP"
        } catch (e: Exception) {
            false
        } finally {
            val enableNetworkRequest = ModifyInstanceAttributeRequest()
                    .withInstanceId(player.node1.id)
                    .withGroups(player.openedSecurityGroup.id, player.sshSecurityGroup.id)
            ec2.modifyInstanceAttribute(enableNetworkRequest)
        }

    }

}
