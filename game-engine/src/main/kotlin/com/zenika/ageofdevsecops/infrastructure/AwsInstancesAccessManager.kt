package com.zenika.ageofdevsecops.infrastructure

import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest
import com.amazonaws.services.ec2.model.RevokeSecurityGroupIngressRequest
import com.zenika.ageofdevsecops.application.InstancesAccessManager
import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.stereotype.Component

@Component
class AwsInstancesAccessManager(private val players: List<Player>, private val ec2: AmazonEC2) : InstancesAccessManager {
    override fun denySshAccess() {
        players.forEach { player ->
            val revokeSecurityGroupIngressRequest = RevokeSecurityGroupIngressRequest()
                    .withCidrIp("0.0.0.0/0")
                    .withIpProtocol("TCP")
                    .withFromPort(22)
                    .withToPort(22)
                    .withGroupId(player.sshSecurityGroup.id)

            ec2.revokeSecurityGroupIngress(revokeSecurityGroupIngressRequest)
        }
    }

    override fun permitSshAccess() {
        players.forEach { player ->
            val authorizeSecurityGroupIngressRequest = AuthorizeSecurityGroupIngressRequest()
                    .withCidrIp("0.0.0.0/0")
                    .withIpProtocol("TCP")
                    .withFromPort(22)
                    .withToPort(22)
                    .withGroupId(player.sshSecurityGroup.id)
            ec2.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest)
        }
    }
}
